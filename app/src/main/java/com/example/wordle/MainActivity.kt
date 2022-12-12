package com.example.wordle

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var numGuesses = 0
    val wordToGuess = FourLetterWordList.getRandomFourLetterWord()
    var currentUserGuess = 1
    var wordLength = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_main)


        val Result = findViewById<TextView>(R.id.finalAnswer)
        Result.text = wordToGuess
        val editText = findViewById<EditText>(R.id.input)
        val guessButton = findViewById<Button>(R.id.button)
        val resetButton = findViewById<Button>(R.id.resetButton)
        var userGuess = findViewById<TextView>(R.id.TestFactor)
        var checkGuess = findViewById<TextView>(R.id.guess_1_check)

        // when press "Enter" key on your keyboard
        editText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (editText.length() < wordLength) {
                    displayLengthErrorMessage(editText)
                }
                else {
                    // Make submit guess function for all event listeners
//                    userGuess = updateUserGuess(currentUserGuess)
                    checkGuess = updateGuessCheck(currentUserGuess)
                    numGuesses++
                    currentUserGuess++
                    editText.hideKeyboard()
                    userGuess.text = editText.text.toString().uppercase()
                    editText.text.clear()

                    checkGuess(userGuess.text.toString(), checkGuess)
                    if (checkIfWin(checkGuess.text.toString())) {
                        Toast.makeText(this, "Congratulations, you won!", Toast.LENGTH_SHORT).show()
                        Result.visibility = View.VISIBLE
                        resetButton.visibility = View.INVISIBLE
                        editText.isFocusable = false
                        guessButton.text = "Restart"
                        guessButton.setOnClickListener {
                            overridePendingTransition(0,0)
                            finish()
                            overridePendingTransition(0,0)
                            startActivity(intent)
                        }
                    }
                    else if (outOfGuesses(numGuesses)) {
                        Result.visibility = View.VISIBLE
                        resetButton.visibility = View.INVISIBLE
                        editText.isFocusable = false


                        guessButton.text = "Restart"
                        guessButton.setOnClickListener {
                            overridePendingTransition(0,0)
                            finish()
                            overridePendingTransition(0,0)
                            startActivity(intent)
                            Toast.makeText(applicationContext, "Game Reset", Toast.LENGTH_SHORT).show()
                        }
                    }
                    return@OnKeyListener true
                }
            }
            false
        })

        // Handle input when using the "Go" key on the keyboard.
        editText.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                if (editText.length() < wordLength) {
                    displayLengthErrorMessage(editText)
                }
                else {
//                    userGuess = updateUserGuess(currentUserGuess)
                    checkGuess = updateGuessCheck(currentUserGuess)
                    numGuesses++
                    currentUserGuess++
                    editText.hideKeyboard()
                    userGuess.text = editText.text.toString().uppercase()
                    editText.text.clear()

                    checkGuess(userGuess.text.toString(), checkGuess)
                    if (checkIfWin(checkGuess.text.toString())) {
                        Toast.makeText(this, "YOU WIN!", Toast.LENGTH_SHORT).show()

                        Result.visibility = View.VISIBLE
                        resetButton.visibility = View.INVISIBLE
                        editText.isFocusable = false
                        guessButton.text = "Restart"
                        guessButton.setOnClickListener {
                            overridePendingTransition(0,0)
                            finish()
                            overridePendingTransition(0,0)
                            startActivity(intent)
                        }
                    }
                    else if (outOfGuesses(numGuesses)) {
                        Result.visibility = View.VISIBLE
                        resetButton.visibility = View.INVISIBLE
                        editText.isFocusable = false


                        guessButton.text = "Restart"
                        guessButton.setOnClickListener {
                            overridePendingTransition(0,0)
                            finish()
                            overridePendingTransition(0,0)
                            startActivity(intent)
                            Toast.makeText(applicationContext, "Game Restarted", Toast.LENGTH_SHORT).show()
                        }
                    }

                    return@OnEditorActionListener true
                }
            }
            false
        })

        // Handle input when using the "GUESS" button
        guessButton.setOnClickListener {
            if (editText.length() < wordLength) {
                displayLengthErrorMessage(editText)
            }
            else {
//                userGuess = updateUserGuess(currentUserGuess)
                checkGuess = updateGuessCheck(currentUserGuess)
                numGuesses++
                currentUserGuess++
                editText.hideKeyboard()
                userGuess.text = editText.text.toString().uppercase()
                editText.text.clear()

                checkGuess(userGuess.text.toString(), checkGuess)
                if (checkIfWin(checkGuess.text.toString())) {
                    Toast.makeText(this, "Congratulations, you won!", Toast.LENGTH_SHORT).show()
                    Result.visibility = View.VISIBLE
                    resetButton.visibility = View.INVISIBLE
                    editText.isFocusable = false
                    guessButton.text = "Restart"
                    guessButton.setOnClickListener {
                        overridePendingTransition(0,0)
                        finish()
                        overridePendingTransition(0,0)
                        startActivity(intent)
                    }
                }
                else if (outOfGuesses(numGuesses)) {
                    Result.visibility = View.VISIBLE
                    resetButton.visibility = View.INVISIBLE
                    editText.isFocusable = false

                    guessButton.setText("Restart")
                    guessButton.setOnClickListener {
                        overridePendingTransition(0,0)
                        finish()
                        overridePendingTransition(0,0)
                        startActivity(intent)
                        Toast.makeText(applicationContext, "Game Restarted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        resetButton.setOnClickListener {

            overridePendingTransition(0,0)
            finish()
            overridePendingTransition(0,0)
            startActivity(intent)
            Toast.makeText(applicationContext, "Game Restarted", Toast.LENGTH_SHORT).show()
        }
    }

    object FourLetterWordList {
        // List of most common 4 letter words from: https://7esl.com/4-letter-words/
        val fourLetterWords =
            "Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Sale,Seat,Shop,Show,Side,Sign,Site,Size,Skin,Sort,Star,Step,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Call,Care,Cast,Come,Cook,Cope,Cost,Dare,Deal,Deny,Draw,Drop,Earn,Face,Fail,Fall,Fear,Feel,Fill,Find,Form,Gain,Give,Grow,Hang,Hate,Have,Head,Hear,Help,Hide,Hold,Hope,Hurt,Join,Jump,Keep,Kill,Know,Land,Last,Lead,Lend,Lift,Like,Link,Live,Look,Lose,Love,Make,Mark,Meet,Mind,Miss,Move,Must,Name,Need,Note,Open,Pass,Pick,Plan,Play,Pray,Pull,Push,Read,Rely,Rest,Ride,Ring,Rise,Risk,Roll,Rule,Save,Seek,Seem,Sell,Send,Shed,Show,Shut,Sign,Sing,Slip,Sort,Stay,Step,Stop,Suit,Take,Talk,Tell,Tend,Test,Turn,Vary,View,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Will,Wish,Work,Able,Back,Bare,Bass,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Dumb,Easy,Evil,Fair,Fast,Fine,Firm,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Half,Hard,Head,High,Holy,Huge,Just,Keen,Kind,Last,Late,Lazy,Like,Live,Lone,Long,Loud,Main,Male,Mass,Mean,Mere,Mild,Nazi,Near,Neat,Next,Nice,Okay,Only,Open,Oral,Pale,Past,Pink,Poor,Pure,Rare,Real,Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Then,Thin,Tidy,Tiny,Tory,Ugly,Vain,Vast,Very,Vice,Warm,Wary,Weak,Wide,Wild,Wise,Zero,Ably,Afar,Anew,Away,Back,Dead,Deep,Down,Duly,Easy,Else,Even,Ever,Fair,Fast,Flat,Full,Good,Half,Hard,Here,High,Home,Idly,Just,Late,Like,Live,Long,Loud,Much,Near,Nice,Okay,Once,Only,Over,Part,Past,Real,Slow,Solo,Soon,Sure,That,Then,This,Thus,Very,When,Wide"

        // Returns a list of four letter words as a list
        fun getAllFourLetterWords(): List<String> {
            return fourLetterWords.split(",")
        }

        // Returns a random four letter word from the list in all caps
        fun getRandomFourLetterWord(): String {
            val allWords = getAllFourLetterWords()
            val randomNumber = (0..allWords.size).shuffled().last()
            return allWords[randomNumber].uppercase()
        }
    }

    private fun checkGuess(guess: String, textView : TextView) {

        val sb = SpannableString(guess.toString())

        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                val fColor = ForegroundColorSpan(Color.GREEN)
                sb.setSpan(fColor,i, i+1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            }
            else if (guess[i] in wordToGuess) {
                val fColor = ForegroundColorSpan(Color.BLUE)
                sb.setSpan(fColor,i, i+1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            }
            else {
                val fColor = ForegroundColorSpan(Color.RED)
                sb.setSpan(fColor,i, i+1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            }
        }
        textView.text = sb
    }

    // Win Condition met?
    private fun checkIfWin(string : String): Boolean {
        if (string == wordToGuess) {
            // Win screen Implementation!!!!!!!!
            return true
        }
        return false
    }

    // If word length error, clear the EditText field and display an error message
    private fun displayLengthErrorMessage(editText : EditText) {
        editText.hideKeyboard()
        editText.text.clear()
        Toast.makeText(applicationContext, "Your guess has to be " + wordLength + " letters long", Toast.LENGTH_SHORT).show()
    }

//    // Updates the guess TextView to the next guess that is to be updated
//    private fun updateUserGuess(currentUserGuess: Int): TextView? {
//        val guessID = resources.getIdentifier("user_guess_$currentUserGuess", "id", packageName)
//        return findViewById<TextView>(guessID)
//    }

    //Updates the guess feedback TextView to the next feedback that is to be updated
    private fun updateGuessCheck(currentUserGuess: Int): TextView? {
        val guessCheckID = resources.getIdentifier("guess_feedback_$currentUserGuess", "id", packageName)
        return findViewById<TextView>(guessCheckID)
    }

    // If Cpu Wins Condition!!!!!!!!!!!!!!!


    // Check if the game is out of guesses.
    private fun outOfGuesses(numGuesses: Int): Boolean {
        if (numGuesses == 3) {
            Toast.makeText(this, "No more guesses, TIE!", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }


    // Function to force hide the soft keyboard.
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
