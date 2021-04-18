package com.example.Agenda

class Contato {
    var id: Int?;
    var nome: String;
    var email: String;
    var telefone: String;

    constructor(id: Int?,nome: String, email: String, telefone: String) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }
}
