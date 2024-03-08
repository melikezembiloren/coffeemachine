private const val NTC_CHECK_INTERVAL = 3_000L // NTC is checked every second
private const val TIMEOUT_DURATION = 12_000L // The coffee machine is turned off in 2 minutes. (2 minutes = 120000 milliseconds)
private const val NTC_MAX = 1023//1023
private const val DESIRED_VALUE_NTC = 700 // 85 Degree
private const val MILLISECOND_TO_SECOND = 1000
private const val TURN_ON = 1
private const val TURN_OFF = 0


var statusBtn: Boolean = false
var statusSystem: Boolean = false
var statusTimer: Boolean = false
var startTime: Long = 0

var totalTime: Long = 0
var LasttotalTime: Long = TIMEOUT_DURATION+1

fun main(){

    while (true){
        if(statusSystem == false) {
            buttonControl()
        }
        timeControl()
        ntcControl()

    }

}

fun buttonControl() {

    println("Press button!")
    val userInputString: String = readln()
    val userInput: Int = userInputString.toIntOrNull()?:TURN_OFF

    when (userInput) {
        TURN_ON -> {
            startTime = System.currentTimeMillis()
            println("machine is working")
            statusBtn = true
            statusSystem = true
        }
        TURN_OFF -> {
            println("machine is not working")
            statusBtn = false
        }
        else -> {
            println("error")
            statusBtn = false
        }
    }

}

fun timeCounter(duration: Long = TIMEOUT_DURATION){

    var currentTime:Long

    currentTime = System.currentTimeMillis()

     if (statusSystem == true &&statusTimer == true && (currentTime - startTime < duration)) {



        totalTime = (currentTime - startTime)/ MILLISECOND_TO_SECOND

         if (LasttotalTime == totalTime)
         {

         }
         else{
             println("$totalTime")
         }


    }
     else
    {
         statusSystem = false
         statusTimer = false
        println("machine is not working")
     }

}


//When time counter reaches the duration, the machine shuts down
fun timeControl(){

    if (statusBtn == true){
        statusTimer = true
        timeCounter()
      //  println("timeControl btn flag true")
    }
    else if (statusBtn == false){
        println("timeControl btn flag false")
    }

}

//The NTC value is being obtained from the user at regular intervals, and when this value reaches the desired maximum, the machine shuts down.
fun ntcControl()
{

    if (LasttotalTime == totalTime)
    {

    }
    else{
        LasttotalTime = totalTime
        if(statusBtn == true) {
            
            // println("enter a ntc value")
            // val ntcInput: String? = readLine()
            // var ntc = ntcInput?.toIntOrNull() ?: 0

            val ntc = (0..NTC_MAX).random()
            println("NTC Değeri: $ntc")


            if (ntc >= DESIRED_VALUE_NTC) {
                statusSystem = false
                println("Reached 85 degrees")
                println("coffee machine is not working")

            }
        }
    }

}

