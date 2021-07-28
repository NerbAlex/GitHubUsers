package ru.inc.myapplication.extensions

import java.util.logging.Logger

private const val activateTest = true


fun Logger.test(log: String) {
    if (activateTest) {
        this.info("MVP_test: $log")
    }
}