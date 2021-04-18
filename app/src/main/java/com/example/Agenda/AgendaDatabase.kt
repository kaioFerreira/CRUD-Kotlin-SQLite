package com.example.Agenda

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AgendaDatabase (ctx: Context): SQLiteOpenHelper(ctx,"agenda.db",null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE contato (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, email TEXT, telefone TEXT);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS contato"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun create(item: Contato): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("nome", item.nome)
        values.put("email", item.email)
        values.put("telefone", item.telefone)

        val _success = db.insert("contato",null,values)
        return (("$_success").toInt() != -1)
    }

    fun read(): ArrayList<Contato> {
        val itemList = ArrayList<Contato>()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM contato"
        val cursor = db.rawQuery(selectQuery, null)

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val item = Contato(
                            id = cursor.getInt(cursor.getColumnIndex("id")),
                            nome = cursor.getString(cursor.getColumnIndex("nome")),
                            email = cursor.getString(cursor.getColumnIndex("email")),
                            telefone = cursor.getString(cursor.getColumnIndex("telefone")));
                    itemList.add(item);
                }while(cursor.moveToNext())
            }
        }
        cursor.close()
        return itemList
    }

    fun update(item: Contato): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("nome", item.nome)
        values.put("email", item.email)
        values.put("telefone", item.telefone)

        val _success = db.update("contato",values, "id = " + item.id, null)
        return (("$_success").toInt() != -1)
    }

    fun delete(item: Contato): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("id", item.id)
        val success = db.delete("contato", "id = " + item.id, null)
        return (("$success").toInt() != -1)
    }

}