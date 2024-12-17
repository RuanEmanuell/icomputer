function abrirModal(option) {
    const dialog = document.querySelector("#" + option + "Modal");
    dialog.showModal();
    zerarInputs();
}

function fecharModal(option) {
    const dialog = document.querySelector("#" + option + "Modal");
    dialog.close();
}

async function carregarUsuarios() {
    htmx.ajax('GET', 'http://localhost:8080/pages/fragment/lista-usuarios', {
        target: '#usuarios-container'
    });
}

async function adicionarUsuario() {
    const data = {
        nome: document.querySelector("#nomeInput").value,
        email: document.querySelector("#emailInput").value,
        senha: document.querySelector("#senhaInput").value,
        endereco: document.querySelector("#enderecoInput").value,
        permissaoAdmin: document.querySelector("#permissaoAdminInput").value
    };

    try {
        const response = await fetch("http://localhost:8080/usuarios", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

    } catch (error) {
        console.error("Erro na requisição:", error);
    }

    fecharModal('add');
    await carregarUsuarios();
}

async function deletarUsuario() {
    const idUsuario = document.querySelector("#deleteIdInput").value;
    try {
        const response = await fetch(`http://localhost:8080/usuarios/${idUsuario}`, {
            method: "DELETE"
        });

    } catch (error) {
        console.error("Erro na requisição:", error);
    }

    fecharModal('delete');
    await carregarUsuarios();
}

async function editarUsuario() {
    const idUsuario = document.querySelector("#editIdInput").value;
    const data = {
        nome: document.querySelector("#editNomeInput").value,
        email: document.querySelector("#editEmailInput").value,
        endereco: document.querySelector("#editEnderecoInput").value,
        permissaoAdmin: document.querySelector("#editPermissaoAdminInput").value
    };

    try {
        const response = await fetch(`http://localhost:8080/usuarios/${idUsuario}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

    } catch (error) {
        console.error("Erro na requisição:", error);
    }

    fecharModal('edit');
    await carregarUsuarios();
}

async function zerarInputs() {
    const todosInputs = document.querySelectorAll("input");

    todosInputs.forEach((input) => {
        input.value = "";
    })
}

async function login() {
    const email = document.querySelector("#emailInput").value;
    const senha = document.querySelector("#senhaInput").value;
    try {
        const response = await fetch("http://localhost:8080/usuarios/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, senha })
        });

        if (response.ok) {
            registrarLoginLocalmente("Teste");
            alert("Login realizado com sucesso");
            window.location.href = "/pages/home";
        } else {
            alert("Erro ao fazer login! Usuário não existente.");
        }
    } catch (error) {
        alert("Erro ao tentar fazer login.");
    }
}

async function fazerCadastro() {
    const nome = document.getElementById('nomeInput').value;
    const email = document.getElementById('emailInput').value;
    const senha = document.getElementById('senhaInput').value;
    const endereco = document.getElementById('enderecoInput').value;

    if (nome && email && senha && endereco) {
        try {
            const data = {
                nome: nome,
                email: email,
                senha: senha,
                endereco: endereco
            };

            const response = await fetch("http://localhost:8080/usuarios", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                registrarLoginLocalmente("Teste");
                alert("Usuário criado com sucesso!");
                window.location.href = "/pages/home";
            } else {
                alert("Erro ao criar usuário! Email já em uso!");
            }

        } catch (error) {
            alert("Erro ao criar usuário!");
        }
    } else {
        alert('Preencha todos os campos!');
    }

}

async function registrarLoginLocalmente(usuario){
    localStorage.setItem("login", JSON.stringify(usuario));
}