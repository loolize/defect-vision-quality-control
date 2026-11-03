import cv2
import json
from datetime import datetime

def load_image(path):
    image = cv2.imread(path, cv2.IMREAD_GRAYSCALE)

    if image is None:
        print("Не удалось открыть изображение")
        return None

    return image


def check_image_quality(image):
    brightness = image.mean()
    sharpness = cv2.Laplacian(image, cv2.CV_64F).var()

    if brightness < 50 or brightness > 230: return False
    if sharpness < 15: return False

    return True


def get_image_with_retries():
    path = input("Путь к изображению (enter(шаблон)): ")

    if path == "": path = "src/images/blister.png"

    for attempt in range(1, 4):
        print("\nПопытка", attempt)

        image = load_image(path)

        if image is not None:
            if check_image_quality(image):
                print("Изображение пригодно для проверки")
                return image, path

        print("Изображение не прошло проверку качества")

        if attempt < 3:
            path = input("Введите путь к новому изображению: ")

    return None, path


def load_template(path):
    with open(path, "r", encoding="utf-8") as file:
        data = json.load(file)

    return data




def analyze_cells(image, cells):
    results = []
    n = len(cells)
    i = 0

    while i < n:
        cell = cells[i]
        cell_id = cell["cell_id"]

        x = cell["x"]
        y = cell["y"]
        width = cell["width"]
        height = cell["height"]

        pixels = []
        for row in image[y:y + height]:
            pixels.extend(row[x:x + width])

        p = len(pixels)
        dark_pixel_count = 0
        j = 0

        while j < p:
            if pixels[j] < 150:
                dark_pixel_count += 1
            j += 1

        dark_pixel_ratio = dark_pixel_count / p

        if dark_pixel_ratio < 0.06:
            state = "empty"
        elif dark_pixel_ratio >= 0.18:
            state = "filled"
        else:
            state = "uncertain"

        results.append((cell_id, state))
        i += 1

    return results


def make_preliminary_decision(results):
    empty_cells = []
    uncertain_cells = []
    i = 0
    n = len(results)

    while i < n:
        cell_id, state = results[i]

        if state == "empty":
            empty_cells.append(cell_id)
        elif state == "uncertain":
            uncertain_cells.append(cell_id)

        i += 1

    if len(uncertain_cells) > 0:
        status = "Требуется повторная проверка"
    elif len(empty_cells) > 0:
        status = "Обнаружена пустая ячейка"
    else:
        status = "Полный"

    return status, empty_cells, uncertain_cells




def confirm_result(status):
    print("\nПредварительное заключение:", status)

    answer = input("Подтвердить результат? (да/нет): ").strip().lower()

    if answer == "нет":
        print("1. Полный")
        print("2. Обнаружена пустая ячейка")
        print("3. Требуется повторная проверка")

        choice = input("Выберите правильный результат: ")

        if choice == "1":
            status = "Полный"
        elif choice == "2":
            status = "Обнаружена пустая ячейка"
        elif choice == "3":
            status = "Требуется повторная проверка"

    return status



def manual_inspection():
    print("\nАвтоматическая проверка не выполнена")
    print("Осмотрите блистер вручную")
    print("1. Полный")
    print("2. Обнаружена пустая ячейка")
    print("3. Требуется повторная проверка")

    while True:
        choice = input("Введите результат проверки: ")

        if choice == "1":
            return "Полный"
        elif choice == "2":
            return "Обнаружена пустая ячейка"
        elif choice == "3":
            return "Требуется повторная проверка"
        else:
            print("Введите число от 1 до 3")


def specialist_review(status):
    print("\nРазбор спорного случая")
    print("Текущий результат:", status)

    name = input("Введите имя специалиста: ")

    print("1. Полный")
    print("2. Обнаружена пустая ячейка")
    print("3. Требуется повторная проверка")

    while True:
        choice = input("Решение специалиста: ").strip()

        if choice == "1":
            return "Полный"
        elif choice == "2":
            return "Обнаружена пустая ячейка"
        elif choice == "3":
            return "Требуется повторная проверка"
        else:
            print("Введите число от 1 до 3")


def check_specialist_review(status):
    answer = input("\nТребуется разбор специалистом? (да/нет): ")
    answer = answer.strip().lower()

    if answer == "да":
        status = specialist_review(status)

    return status

 
def save_inspection(path, template_data, image_path,
                    results, automatic_status, final_status,
                    operator_name):

    now = datetime.now().isoformat(timespec="seconds")

    data = {
        "series":[
            {
                "series_id": 1,
                "series_number": "TEST-001"
            }
        ],
        "blisters": [
            {
                "blister_id": 1,
                "series_id": 1,
                "template_id": 1
            }
        ],
        "templates":[
            template_data["template"]
        ],
        "template_cells": template_data["cells"],
        "images":[
            {
                "image_id": 1,
                "blister_id": 1,
                "file_path": image_path,
                "captured_at": None
            }
        ],
        "operators":[
            {
                "operator_id": 1,
                "display_name": operator_name
            }
        ],
        "inspections":[
            {
                "inspection_id": 1,
                "image_id": 1,
                "operator_id": 1,
                "created_at": now,
                "automatic_status": automatic_status,
                "final_status": final_status,
                "confirmed_at": now
            }
        ],
        "cell_results": []
    }

    for cell_id, state in results:
        data["cell_results"].append({
            "inspection_id": 1,
            "cell_id": cell_id,
            "automatic_state": state
        })

    with open(path, "w", encoding="utf-8") as file:
        json.dump(data, file, ensure_ascii=False, indent=4)

    print("Данные записаны в файл:", path)
    print("Время сохранения:", now)








def main():
    template_data = load_template("src/template.json")

    image, image_path = get_image_with_retries()

    if image is None:
        operator_name = input("\nВведите имя оператора: ")
        final_status = manual_inspection()

        final_status = check_specialist_review(final_status)

        save_inspection(
            "src/inspection.json",
            template_data,
            image_path,
            [],
            "Изображение непригодно",
            final_status,
            operator_name
        )

        print("\nИтоговое заключение:", final_status)
        print("Результат сохранен в inspection.json")
        return

    print("\nКачество изображения проверено")

    cells = template_data["cells"]

    print("Изображение загружено")
    print("Количество ячеек:", len(cells))

    results = analyze_cells(image, cells)

    print("\nРезультаты проверки:")

    for cell_id, state in results:
        print("Ячейка", cell_id, "-", state)

    automatic_status, empty_cells, uncertain_cells = (
        make_preliminary_decision(results)
    )

    print("\nПустые ячейки:", empty_cells)
    print("Неопределенные ячейки:", uncertain_cells)

    operator_name = input("\nВведите имя оператора: ")

    final_status = confirm_result(automatic_status)
    final_status = check_specialist_review(final_status)

    save_inspection(
        "src/inspection.json",
        template_data,
        image_path,
        results,
        automatic_status,
        final_status,
        operator_name
    )

    print("\nИтоговое заключение:", final_status)
    print("Результаты сохранены в inspection.json")


if __name__ == "__main__":
    main()




