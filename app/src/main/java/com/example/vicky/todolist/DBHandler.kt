package com.example.vicky.todolist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.vicky.todolist.DTO.Movement

class DBHandler(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createMoneyAppTable = "CREATE TABLE $TABLE_MONEYAPP ( " +
                "$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_CREATED_AT datetime DEFAULT CURRENT_TIMESTAMP," +
                //"$COL_USER_NAME varchar," +
                "$COL_DATE varchar," +
                "$COL_NAME varchar);"

        db.execSQL(createMoneyAppTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


    fun addMovement(movement: Movement): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, movement.name)
        // cv.put(COL_USER_NAME, movement.username)
        cv.put(COL_DATE, movement.date)
        val result = db.insert(TABLE_MONEYAPP, null, cv)
        return result != (-1).toLong()
    }

    fun updateMovement(movement: Movement) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, movement.name)
        //  cv.put(COL_USER_NAME, movement.username)
        cv.put(COL_DATE, movement.date)
        db.update(TABLE_MONEYAPP, cv, "$COL_ID=?", arrayOf(movement.id.toString()))
    }


    fun deleteMovement(MovementId: Long) {
        val db = writableDatabase
        db.delete(TABLE_MONEYAPP, "$COL_ID=?", arrayOf(MovementId.toString()))
    }

    //Ojo
    fun getMovements(): MutableList<Movement> {
        val result: MutableList<Movement> = ArrayList()
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * from $TABLE_MONEYAPP", null)
        if (queryResult.moveToFirst()) {
            do {
                val movement = Movement()
                movement.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                movement.name = queryResult.getString(queryResult.getColumnIndex(COL_NAME))
                //   movement.username = queryResult.getString(queryResult.getColumnIndex(COL_USER_NAME))
                movement.date = queryResult.getString(queryResult.getColumnIndex(COL_DATE))

                result.add(movement)

            } while (queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }


}