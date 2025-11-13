fun main() {
    println("=== Меню программ ===\n")

    while (true) {
        println("Выберите программу для запуска:")
        println("1. Задание 1")
        println("2. Задание 2")
        println("3. Задание 3")
        println("4. Задание 4")
        println("5. Задание 5")
        println("0. Выход")

        print("\nВведите номер программы: ")
        val choice = readln()

        when (choice) {
            "1" -> runProgram1()
            "2" -> runProgram2()
            "3" -> runProgram3()
            "4" -> runProgram4()
            "5" -> runProgram5()
            "0" -> {
                println("Программа завершена.")
                break
            }
            else -> println("Ошибка: введите число от 0 до 5!")
        }

        println("\n" + "=".repeat(40) + "\n")
    }
}

fun runProgram1()
{
    print("Введите количество строк: ")
    val rows = readLine()?.toIntOrNull() ?: 0

    print("Введите количество столбцов: ")
    val cols = readLine()?.toIntOrNull() ?: 0

    if (rows <= 0 || cols <= 0) {
        println("Некорректные размеры массива")
        return
    }

    val matrix = Array(rows) { IntArray(cols) }

    println("\nВведите $rows строк по $cols трехзначных чисел:")
    for (i in 0 until rows) {
        var validInput = false
        while (!validInput) {
            print("Строка ${i + 1}: ")
            val input = readLine()
            val numbers = input?.trim()?.split("\\s+".toRegex())

            if (numbers?.size == cols) {
                var allValid = true
                for (j in 0 until cols) {
                    val num = numbers[j].toIntOrNull()
                    if (num == null || num < 100 || num > 999) {
                        println("Ошибка: '$num' не является трехзначным числом")
                        allValid = false
                        break
                    }
                    matrix[i][j] = num
                }
                validInput = allValid
            } else {
                println("Ошибка: введено неверное количество чисел. Ожидается: $cols")
            }
        }
    }

    val uniqueDigits = mutableSetOf<Char>()

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            val numberStr = matrix[i][j].toString()
            for (digit in numberStr) {
                uniqueDigits.add(digit)
            }
        }
    }

    println("\n---Двумерный массив---")
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            print("${matrix[i][j]}\t")
        }
        println()
    }

    println("\nВ массиве использовано ${uniqueDigits.size} различных цифр")
}


fun runProgram2()
{
    println("Введите значение n для массива из n на n элементов:")
    val c = readln().toInt()
    val matrix = Array(c) { IntArray(c) }

    println("=== Заполнение массива $c на $c ===")
    println("Введите ${c*c} целых чисел:")

    for (i in 0 until c) {
        println("\n--- Строка ${i + 1} ---")
        for (j in 0 until c) {
            var inputValid = false
            while (!inputValid) {
                print("Элемент [${i + 1}][${j + 1}]: ")
                val input = readLine()

                if (input.isNullOrBlank()) {
                    println("Ошибка: введите число!")
                    continue
                }

                try {
                    matrix[i][j] = input.toInt()
                    inputValid = true
                } catch (e: NumberFormatException) {
                    println("Ошибка: '$input' не является целым числом. Попробуйте снова.")
                }
            }
        }
    }

    println("\n=== Введенный массив ===")
    printMatrix(matrix)

    val isSymmetric = isMatrixSymmetric(matrix)

    println("=== Результат проверки ===")
    if (isSymmetric) {
        println("Массив является симметричным относительно главной диагонали")
    } else {
        println("Массив НЕ является симметричным относительно главной диагонали")
    }
}

fun isMatrixSymmetric(matrix: Array<IntArray>): Boolean {
    val n = matrix.size

    for (i in 0 until n) {
        for (j in i + 1 until n) {
            if (matrix[i][j] != matrix[j][i]) {
                println("\nНайдены несимметричные элементы:")
                println("matrix[${i + 1}][${j + 1}] = ${matrix[i][j]} != matrix[${j + 1}][${i + 1}] = ${matrix[j][i]}")
                return false
            }
        }
    }

    return true
}

fun printMatrix(matrix: Array<IntArray>) {

    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            print("${matrix[i][j].toString().padEnd(9)} ")
        }
        println()
    }
    println()
}

fun runProgram3()
{
    val alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"

    println("Выберите действие:")
    println("1 - Зашифровать")
    println("2 - Расшифровать")
    val choice = readLine()?.toIntOrNull() ?: run {
        println("Ошибка: введите число 1 или 2")
        return
    }

    if (choice != 1 && choice != 2) {
        println("Ошибка: выберите 1 или 2")
        return
    }

    println("Введите ключевое слово:")
    val key = readLine()?.uppercase()?.takeIf { it.isNotBlank() } ?: run {
        println("Ошибка: ключевое слово не может быть пустым")
        return
    }

    println("Введите текст:")
    val text = readLine()?.uppercase()?.takeIf { it.isNotBlank() } ?: run {
        println("Ошибка: текст не может быть пустым")
        return
    }

    val result = if (choice == 1) {
        encrypt(text, key, alphabet)
    } else {
        decrypt(text, key, alphabet)
    }

    println("Результат: $result")
}

fun encrypt(text: String, key: String, alphabet: String): String {
    var result = ""
    var keyIndex = 0

    for (char in text) {
        if (char == ' ') {
            result += ' '
            continue
        }

        val textPos = alphabet.indexOf(char)
        val keyChar = key[keyIndex % key.length]
        val shift = alphabet.indexOf(keyChar) + 1

        val newPos = (textPos + shift) % alphabet.length
        result += alphabet[newPos]

        keyIndex++
    }

    return result
}

fun decrypt(text: String, key: String, alphabet: String): String {
    var result = ""
    var keyIndex = 0

    for (char in text) {
        if (char == ' ') {
            result += ' '
            continue
        }

        val textPos = alphabet.indexOf(char)
        val keyChar = key[keyIndex % key.length]
        val shift = alphabet.indexOf(keyChar) + 1

        val newPos = (textPos - shift + alphabet.length) % alphabet.length
        result += alphabet[newPos]

        keyIndex++
    }

    return result
}

/*fun runProgram3() {
    val alphabet = mapOf(
        'А' to 21, 'Б' to 13, 'В' to 4,  'Г' to 20, 'Д' to 22, 'Е' to 1,  'Ё' to 25,
        'Ж' to 12, 'З' to 24, 'И' to 14, 'Й' to 2,  'К' to 28, 'Л' to 9,  'М' to 23,     //по конкретному примеру
        'Н' to 3,  'О' to 29, 'П' to 6,  'Р' to 16, 'С' to 15, 'Т' to 11, 'У' to 26,
        'Ф' to 5,  'Х' to 30, 'Ц' to 27, 'Ч' to 8,  'Ш' to 18, 'Щ' to 10, 'Ь' to 33,
        'Ы' to 31, 'Ъ' to 32, 'Э' to 19, 'Ю' to 7,  'Я' to 17
    )

    val reverseAlphabet = mutableMapOf<Int, Char>()
    for ((char, num) in alphabet) {
        reverseAlphabet[num] = char
    }

    println("1 - Зашифровать")
    println("2 - Расшифровать")
    val choice = readLine()!!.toInt()

    print("Текст: ")
    val text = readLine()!!.uppercase()

    print("Ключ: ")
    val key = readLine()!!.uppercase()

    var result = ""

    for (i in text.indices) {
        val char = text[i]
        val keyChar = key[i % key.length]

        if (char !in alphabet) {
            result += char
            continue
        }

        val charNum = alphabet[char]!!
        val shift = alphabet[keyChar]!!

        if (choice == 1) {
            val newNum = (charNum + shift - 1) % 33 + 1
            result += reverseAlphabet[newNum]!!
        } else {
            val originalNum = (charNum - shift + 33 - 1) % 33 + 1
            result += reverseAlphabet[originalNum]!!
        }
    }

    println("Результат: $result")
}*/

fun runProgram4()
{
    println("Введите первый массив чисел через пробел:")
    val array1 = readLine()?.trim()?.split(" ")?.map { it.toInt() } ?: emptyList()

    println("Введите второй массив чисел через пробел:")
    val array2 = readLine()?.trim()?.split(" ")?.map { it.toInt() } ?: emptyList()

    val result = findIntersection(array1, array2)
    if (result.isEmpty()) {
        println("Нет пересечений")
    } else {
        println("Пересечение массивов: ${result.joinToString()}")
    }
}

fun findIntersection(array1: List<Int>, array2: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    val tempArray2 = array2.toMutableList()

    for (num in array1) {
        if (num in tempArray2) {
            result.add(num)
            tempArray2.remove(num)
        }
    }
    if (result.isNotEmpty()) {
        return result
    } else {
        return listOf()
    }
}

fun runProgram5() {
    println("Введите слова через пробел:")
    val input = readLine() ?: ""

    val words = input.trim().split(" ")

    val groupedWords = groupWords(words)

    println("\nСгруппированные слова:")
    printGroups(groupedWords)
}

fun groupWords(words: List<String>): Map<String, List<String>> {
    val result = mutableMapOf<String, MutableList<String>>()

    for (word in words) {
        val sortedKey = word.toCharArray().sorted().joinToString()

        if (!result.containsKey(sortedKey)) {
            result[sortedKey] = mutableListOf()
        }

        result[sortedKey]?.add(word)
    }

    return result
}

fun printGroups(groups: Map<String, List<String>>) {
    for (group in groups.values) {
        println(group.joinToString(", "))
    }
}
