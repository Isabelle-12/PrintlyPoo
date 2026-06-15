package com.example.printlypoo.model.usuario;

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private String cpfCnpj;
    private String telefone;
    private String cep;
    private String cidade;
    private String estado;
    private String endereco;


    public Usuario() {}

    public Usuario(String nome, String email, String senha,
                   String cpfCnpj, String telefone, String cep,
                   String cidade, String estado, String endereco) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.endereco = endereco;

    }


    public String paraTexto() {
        return nome + "|" + email + "|" + senha + "|" + cpfCnpj + "|" +
                telefone + "|" + cep + "|" + cidade + "|" + estado + "|" +
                endereco ;
    }


    public static Usuario deTexto(String linha) {
        String[] partes = linha.split("\\|");
        return new Usuario(
                partes[0], // nome
                partes[1], // email
                partes[2], // senha
                partes[3], // cpfCnpj
                partes[4], // telefone
                partes[5], // cep
                partes[6], // cidade
                partes[7], // estado
                partes[8] // endereco

        );
    }

    // Getters e Setters de todos os atributos
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getCpfCnpj() { return cpfCnpj; }
    public void setCpfCnpj(String cpfCnpj) { this.cpfCnpj = cpfCnpj; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    
    @Override
    public String toString() {
        return nome + " | " + email ;
    }
}