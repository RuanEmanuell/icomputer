function abrirModal(option){
    const dialog = document.querySelector("#" + option + "Modal");
    dialog.showModal();
    zerarInputs();
}

function fecharModal(option){
    const dialog = document.querySelector("#" + option + "Modal");
    dialog.close();
}

async function carregarUsuarios(){
    htmx.ajax('GET', 'http://localhost:8080/pages/fragment/lista-usuarios', {
        target: '#usuarios-container'
    });
}

async function adicionarUsuario() {
    const data = {
        nome: document.querySelector("#nomeInput").value,
        email: document.querySelector("#emailInput").value,
        senha: document.querySelector("#senhaInput").value,
        endereco: document.querySelector("#enderecoInput").value
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
        endereco: document.querySelector("#editEnderecoInput").value
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

async function zerarInputs(){
    const todosInputs = document.querySelectorAll("input");

    todosInputs.forEach((input) => {
        input.value = "";
    })
}