package com.myproject.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

/**
 * Для того, чтобы работала кнопка result, необходимо подключить библиотеку
 * Библиотека exp4j разбивает строку на математические действия и выводит результат
 */
class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    // Объявляем переменные
    private lateinit var fieldMathOperation: AppCompatTextView
    private lateinit var fieldResult: AppCompatTextView
    private lateinit var button0: AppCompatTextView
    private lateinit var button1: AppCompatTextView
    private lateinit var button2: AppCompatTextView
    private lateinit var button3: AppCompatTextView
    private lateinit var button4: AppCompatTextView
    private lateinit var button5: AppCompatTextView
    private lateinit var button6: AppCompatTextView
    private lateinit var button7: AppCompatTextView
    private lateinit var button8: AppCompatTextView
    private lateinit var button9: AppCompatTextView
    private lateinit var buttonDot: AppCompatTextView
    private lateinit var buttonMinus: AppCompatTextView
    private lateinit var buttonPlus: AppCompatTextView
    private lateinit var buttonMultiply: AppCompatTextView
    private lateinit var buttonDivide: AppCompatTextView
    private lateinit var buttonOpenBracket: AppCompatTextView
    private lateinit var buttonCloseBracket: AppCompatTextView
    private lateinit var buttonClear: AppCompatTextView
    private lateinit var buttonBack: AppCompatTextView
    private lateinit var buttonResult: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализируем
        fieldMathOperation = findViewById(R.id.math_operation)
        fieldResult = findViewById(R.id.result_text)
        button0 = findViewById(R.id.button_0)
        button1 = findViewById(R.id.button_1)
        button2 = findViewById(R.id.button_2)
        button3 = findViewById(R.id.button_3)
        button4 = findViewById(R.id.button_4)
        button5 = findViewById(R.id.button_5)
        button6 = findViewById(R.id.button_6)
        button7 = findViewById(R.id.button_7)
        button8 = findViewById(R.id.button_8)
        button9 = findViewById(R.id.button_9)
        buttonDot = findViewById(R.id.button_dot)
        buttonMinus = findViewById(R.id.button_minus)
        buttonPlus = findViewById(R.id.button_plus)
        buttonMultiply = findViewById(R.id.button_multiply)
        buttonDivide = findViewById(R.id.button_divide)
        buttonOpenBracket = findViewById(R.id.button_openBracket)
        buttonCloseBracket = findViewById(R.id.button_closeBracket)
        buttonClear = findViewById(R.id.button_clear)
        buttonBack = findViewById(R.id.button_back)
        buttonResult = findViewById(R.id.button_result)

        // Вешаем слушателей
        setClickListeners()
    }

    private fun setTextFields(str: String) {
        // END -> Калькулятор уже работает. Однако, доработаем метод setTextFields, чтобы получаемый результат записывался в верхнюю строку вместо набранного текста
        if (fieldResult.text != "") {
            fieldMathOperation.text = fieldResult.text
            fieldResult.text = ""
        }

        if (fieldMathOperation.text == "0") {
            fieldMathOperation.text = ""
        }

        // append добавляет строку к уже существующей строке
        fieldMathOperation.append(str)
    }

    private fun setClickListeners() {
        // По клику на кнопки, пишем в поле fieldMathOperation соответствующий символ
        button0.setOnClickListener { setTextFields("0") }
        button1.setOnClickListener { setTextFields("1") }
        button2.setOnClickListener { setTextFields("2") }
        button3.setOnClickListener { setTextFields("3") }
        button4.setOnClickListener { setTextFields("4") }
        button5.setOnClickListener { setTextFields("5") }
        button6.setOnClickListener { setTextFields("6") }
        button7.setOnClickListener { setTextFields("7") }
        button8.setOnClickListener { setTextFields("8") }
        button9.setOnClickListener { setTextFields("9") }
        button9.setOnClickListener { setTextFields("9") }

        buttonDot.setOnClickListener {
            val strForSearching = fieldMathOperation.text.toString()
            val dotLocation = strForSearching.indexOf(".")

            // Если возвращается местоположение точки, значит она уже есть в строке и новую мы не ставим
            // Если же точка не найдена, возвращается число -1, тогда мы ставим точку в поле fieldMathOperation
            if (dotLocation < 0) {
                setTextFields(".")
            }
        }

        buttonMinus.setOnClickListener { setTextFields("-") }
        buttonPlus.setOnClickListener { setTextFields("+") }
        buttonMultiply.setOnClickListener { setTextFields("*") }
        buttonDivide.setOnClickListener { setTextFields("/") }
        buttonOpenBracket.setOnClickListener { setTextFields("(") }
        buttonCloseBracket.setOnClickListener { setTextFields(")") }

        buttonClear.setOnClickListener {
            // По клику очищаем оба поля
            fieldMathOperation.text = "0"
            fieldResult.text = ""
        }

        buttonBack.setOnClickListener {
            // По клику убираем один символ из fieldMathOperation и очищаем fieldResult
            val str = fieldMathOperation.text.toString()
            if (str.isNotEmpty()) {
                // Просто обрезаем строку - с 0 индекса и до предпоследнего
                fieldMathOperation.text = str.substring(0, str.length - 1)
            }
            fieldResult.text = ""
        }

        buttonResult.setOnClickListener {
            // Для начала - згрузить библьотеку для обработки введенной строки и математических вычислений
            // При работе могут возникнуть ошибки => try-catch
            try {
                // Сооздаём объект на основе класса ExpressionBuilder из загруженной библиотеки
                // Именно с помощью этого объекта мы сможем вычислять математические действия из обычной строки
                // !!! Как при создании любого билдера, в конце вызываем .build()
                val expressionBuilder = ExpressionBuilder(fieldMathOperation.text.toString()).build()
                // Вызываем функцию, которая позволит высчитать результат из той строки, которую мы передали при создании объекта expressionBuilder
                val result = expressionBuilder.evaluate()

                // Можно уже выводить на экран. Однако, мделаем так, чтобы ".0" выводилось только тогда, когда после точки есть какие-то числа
                val longResult = result.toLong()
                if (result == longResult.toDouble()) {
                    fieldResult.text = longResult.toString()
                } else {
                    fieldResult.text = result.toString()
                }
            } catch (e: Exception) {
                Log.d(TAG, "сообщение: ${e.message}")
            }
        }
    }
}