async function abrirModal(option) {
    const dialog = document.querySelector("#" + option + "Modal");
    dialog.showModal();
    zerarInputs();
}

async function fecharModal(option) {
    const dialog = document.querySelector("#" + option + "Modal");
    dialog.close();
}

async function carregarVendas() {
    htmx.ajax('GET', 'http://localhost:8080/pages/fragment/lista-vendas', {
        target: '#vendas-container'
    });
}

async function adicionarVenda() {
    const usuarioId = parseInt(document.querySelector("#usuarioInput").value);
    const modeloId = parseInt(document.querySelector("#modeloInput").value);
    const dataVenda = document.querySelector("#dataVendaInput").value;

    // Verificação de campos obrigatórios
    if (isNaN(usuarioId) || usuarioId <= 0) {
        alert('Selecione um usuário válido!');
        return;
    }

    if (isNaN(modeloId) || modeloId <= 0) {
        alert('Selecione um modelo válido!');
        return;
    }

    if (!dataVenda) {
        alert('A data da venda é obrigatória!');
        return;
    }

    const data = {
        usuario: { idUsuario: usuarioId },
        modelo: { idModelo: modeloId },
        dataVenda: dataVenda
    };

    try {
        const response = await fetch("http://localhost:8080/vendas", {
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
    await carregarVendas();
}

async function deletarVenda() {
    const idVenda = document.querySelector("#deleteIdVendaInput").value;
    try {
        const response = await fetch(`http://localhost:8080/vendas/${idVenda}`, {
            method: "DELETE"
        });
    } catch (error) {
        console.error("Erro na requisição:", error);
    }

    fecharModal('delete');
    await carregarVendas();
}

async function editarVenda() {
    const idVenda = document.querySelector("#editIdVendaInput").value;
    const usuarioId = parseInt(document.querySelector("#editUsuarioInput").value);
    const modeloId = parseInt(document.querySelector("#editModeloInput").value);
    const dataVenda = document.querySelector("#editDataVendaInput").value;

    if (isNaN(idVenda) || idVenda <= 0) {
        alert('Selecione uma venda válida!');
        return;
    }

    if (isNaN(usuarioId) || usuarioId <= 0) {
        alert('Selecione um usuário válido!');
        return;
    }

    if (isNaN(modeloId) || modeloId <= 0) {
        alert('Selecione um modelo válido!');
        return;
    }

    if (!dataVenda) {
        alert('A data da venda é obrigatória!');
        return;
    }

    const data = {
        usuario: { idUsuario: usuarioId },
        modelo: { idModelo: modeloId },
        dataVenda: dataVenda
    };

    try {
        const response = await fetch(`http://localhost:8080/vendas/${idVenda}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            alert("Erro ao editar a venda!");
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
    }

    fecharModal('edit');
    await carregarVendas();
}

async function zerarInputs() {
    const todosInputs = document.querySelectorAll("input");

    todosInputs.forEach((input) => {
        input.value = "";
    });
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
  