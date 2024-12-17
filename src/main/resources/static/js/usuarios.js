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

function validarEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

async function adicionarUsuario() {
    const nome = document.querySelector("#nomeInput").value;
    const email = document.querySelector("#emailInput").value;
    const senha = document.querySelector("#senhaInput").value;
    const endereco = document.querySelector("#enderecoInput").value;
    const permissaoAdmin = document.querySelector("#permissaoAdminInput").value;

    if (!nome || nome.length < 3) {
        alert('Nome deve ter pelo menos 3 caracteres!');
        return;
    }

    if (!email || !validarEmail(email)) {
        alert('Por favor, insira um email válido!');
        return;
    }

    if (!senha || senha.length < 6) {
        alert('Senha deve ter pelo menos 6 caracteres!');
        return;
    }

    if (!endereco || endereco.length < 5) {
        alert('Endereço deve ter pelo menos 5 caracteres!');
        return;
    }

    if (!permissaoAdmin) {
        alert('Selecione a permissão de administrador!');
        return;
    }

    const data = {
        nome: nome,
        email: email,
        senha: senha,
        endereco: endereco,
        permissaoAdmin: permissaoAdmin
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
        alert("Erro ao adicionar o usuário!");
    }

    fecharModal('add');
    await carregarUsuarios();
}

function deletarUsuario() {
    const idUsuario = document.getElementById("deleteIdInput").value;

    if (isNaN(idUsuario) || idUsuario <= 0) {
        alert('Selecione um id válido!');
        return;
    }

    fetch(`/usuarios/${idUsuario}`, {
        method: "DELETE",
        headers: { "Content-Type": "application/json" },
    })
    .then(response => response.json())
    .then(data => {
        if (data.selfDelete) {
            alert("Você excluiu sua própria conta. Redirecionando para a página de login...");
            logoutUsuario();
        } else if (data.success) {
            alert(data.message);
            location.reload(); 
        } else {
            alert("Erro: Este usuário tem vendas associadas a ele que precisam ser apagadas primeiro.");
        }
    })
    .catch(error => {
        console.error("Erro ao deletar usuário:", error);
        alert("Erro ao deletar usuário.");
    });
}



async function editarUsuario() {
    const idUsuario = document.querySelector("#editIdInput").value;
    const nome = document.querySelector("#editNomeInput").value;
    const email = document.querySelector("#editEmailInput").value;
    const endereco = document.querySelector("#editEnderecoInput").value;
    const permissaoAdmin = document.querySelector("#editPermissaoAdminInput").value;

    if (isNaN(idUsuario) || idUsuario <= 0) {
        alert('Selecione um id válido!');
        return;
    }

    if (!nome || nome.length < 3) {
        alert('Nome deve ter pelo menos 3 caracteres!');
        return;
    }

    if (!email || !validarEmail(email)) {
        alert('Por favor, insira um email válido!');
        return;
    }

    if (!endereco || endereco.length < 5) {
        alert('Endereço deve ter pelo menos 5 caracteres!');
        return;
    }

    if (!permissaoAdmin) {
        alert('Selecione a permissão de administrador!');
        return;
    }

    const data = {
        nome: nome,
        email: email,
        endereco: endereco,
        permissaoAdmin: permissaoAdmin
    };

    try {
        const response = await fetch(`http://localhost:8080/usuarios/${idUsuario}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        const usuarioLocal = localStorage.getItem("login");

        if (usuarioLocal != null) {
            const usuario = JSON.parse(usuarioLocal);
            if (usuario.idUsuario == idUsuario) {
                alert("Você editou sua própria conta. Voltando para a tela de login...");
                logoutUsuario();
            }
        }

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

    if (!nome || nome.length < 3) {
        alert('Nome deve ter pelo menos 3 caracteres!');
        return;
    }

    if (!email || !validarEmail(email)) {
        alert('Por favor, insira um email válido!');
        return;
    }

    if (!senha || senha.length < 6) {
        alert('Senha deve ter pelo menos 6 caracteres!');
        return;
    }

    if (!endereco || endereco.length < 5) {
        alert('Endereço deve ter pelo menos 5 caracteres!');
        return;
    }


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
  