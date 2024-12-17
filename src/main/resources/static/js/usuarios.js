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

function deletarUsuario() {
    const idUsuario = document.getElementById("deleteIdInput").value;

    fetch(`/usuarios/${idUsuario}`, {
        method: "DELETE",
        headers: { "Content-Type": "application/json" },
    })
    .then(response => response.json())
    .then(data => {
        if (data.selfDelete) {
            alert("Você excluiu sua própria conta. Redirecionando para a página de login...");
            window.location.href = "/pages/login"; // Redireciona para o login
        } else if (data.success) {
            alert(data.message);
            location.reload(); // Recarrega a lista de usuários
        } else {
            alert("Erro: " + data.message);
        }
    })
    .catch(error => {
        console.error("Erro ao deletar usuário:", error);
        alert("Erro ao deletar usuário.");
    });
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
            alert("Login realizado com sucesso!");
            await registrarLoginLocalmente(email);
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
                alert("Usuário criado com sucesso!");
                await registrarLoginLocalmente(email);
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

async function registrarLoginLocalmente(email){
    try {
        const response = await fetch(`http://localhost:8080/usuarios/email/${email}`);
        const data = await response.json();
        if(data != null){
            localStorage.setItem("login", JSON.stringify(data));   
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
    }
}

function verificarUsuarioIsAdmin() {
    const usuarioLocal = localStorage.getItem("login");

    if (usuarioLocal != null) {
        const usuario = JSON.parse(usuarioLocal);

        const isAdmin = usuario.permissaoAdmin === 's';

        if (!isAdmin) {
            alert("Apenas administradores podem acessar essa página!");
            window.location.href = "/pages/home";
        } 
        
    } else {
        window.location.href = "/pages/login";
    }
}

function logoutUsuario(){
    localStorage.removeItem("login");
    window.location.href = "/pages/login";
}

if (window.location.pathname !== "/pages/login" && window.location.pathname !== "/pages/cadastro") {
    verificarUsuarioIsAdmin();
}

document.querySelectorAll('form').forEach(form => {
    form.addEventListener('submit', (event) => {
      event.preventDefault();
    });
  });
  