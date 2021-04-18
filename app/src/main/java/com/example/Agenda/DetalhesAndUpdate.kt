package com.example.Agenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.Agenda.databinding.ActivityDetalhesAndUpdateBinding
import com.example.Agenda.databinding.ActivityMainBinding
import android.widget.TextView

class DetalhesAndUpdate : AppCompatActivity() {
    private lateinit var binding: ActivityDetalhesAndUpdateBinding
    internal var dbHelper = AgendaDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_and_update)

        binding = ActivityDetalhesAndUpdateBinding.inflate(layoutInflater)

        val labelNome: TextView = findViewById(R.id.label_nome) as TextView
        val labelTelefone: TextView = findViewById(R.id.label_telefone) as TextView
        val labelEmail: TextView = findViewById(R.id.label_email) as TextView

        val nome = intent.getStringExtra("nome")

        val contatos = dbHelper.read()

        val item = contatos.filter { it.nome == nome }[0]

        labelNome.text = item.nome;
        labelEmail.text = item.email;
        labelTelefone.text = item.telefone;

        var buttonUpdate = findViewById<Button>(R.id.btn_update)

        buttonUpdate.setOnClickListener {
            var nome = findViewById<EditText>(R.id.label_nome)
            val email = findViewById<EditText>(R.id.label_email)
            val telefone = findViewById<EditText>(R.id.label_telefone)

            val itemUpdate = Contato(id = item.id, nome = nome.text.toString(), email = email.text.toString(), telefone = telefone.text.toString());

            dbHelper.update(itemUpdate)

            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }

        var buttonDelete = findViewById<Button>(R.id.btn_delete)

        buttonDelete.setOnClickListener {

            dbHelper.delete(item)

            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }
    }
}


