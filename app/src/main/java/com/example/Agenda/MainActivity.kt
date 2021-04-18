package com.example.Agenda

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.Agenda.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    internal var dbHelper = AgendaDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val contatosAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        binding.listViewProdutos.adapter = contatosAdapter

        val contatos = dbHelper.read()
        val size = contatos.size
        var count = 0
        while(count < size) {
            contatosAdapter.add(contatos[count].nome)
            count++
        }

        binding.btnInserir.setOnClickListener {
            val nome = binding.txtNome.text.toString()
            val email = binding.txtEmail.text.toString()
            val telefone = binding.txtTelefone.text.toString()

            if (nome.isNotEmpty() && email.isNotEmpty() && telefone.isNotEmpty()) {
                var contato = Contato(id = null, nome = nome, email = email, telefone = telefone);
                contatosAdapter.add(contato.nome)

                binding.txtNome.text.clear()
                binding.txtEmail.text.clear()
                binding.txtTelefone.text.clear()

                binding.txtNome.requestFocus()
                dbHelper.create(contato)
            }
            else {
                if (nome.isEmpty())
                binding.txtNome.error = "Insira um Nome!"
                if (email.isEmpty())
                binding.txtEmail.error = "Insira um Email!"
                if (telefone.isEmpty())
                binding.txtTelefone.error = "Insira um Telefone!"
            }
        }

        binding.listViewProdutos.setOnItemClickListener {
            adapterView: AdapterView<*>, view: View, position: Int, id: Long ->
            binding.listViewProdutos.getChildAt(position)

            var aux = adapterView.getItemAtPosition(position).toString();
            openDetails(aux)

            binding
            true
        }

        binding.listViewProdutos.setOnItemLongClickListener {
            adapterView: AdapterView<*>, view: View, position: Int, id: Long ->
            val nome = contatosAdapter.getItem(position) as String
            binding.listViewProdutos.getChildAt(position).setBackgroundColor(Color.WHITE)
            contatosAdapter.remove(nome)

            val contatos = dbHelper.read()

            val item = contatos.filter { it.nome == nome }[0]

            dbHelper.delete(item)
            true
        }
    }

    private fun openDetails(nome: String) {
        val intent = Intent(this, DetalhesAndUpdate::class.java)
        intent.putExtra("nome",nome)

        startActivity(intent)
    }
}