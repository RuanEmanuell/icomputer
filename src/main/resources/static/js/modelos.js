function abrirModal(option) {
    const dialog = document.querySelector("#" + option + "Modal");
    dialog.showModal();
    zerarInputs();
}

function fecharModal(option) {
    const dialog = document.querySelector("#" + option + "Modal");
    dialog.close();
}

async function carregarModelos() {
    htmx.ajax('GET', 'http://localhost:8080/pages/fragment/lista-modelos', {
        target: '#modelos-container'
    });
}

async function adicionarModelo() {
    const nome = document.querySelector("#nomeInput").value;
    const cpu = document.querySelector("#cpuInput").value;
    const ram = parseInt(document.querySelector("#ramInput").value);
    const ssd = parseInt(document.querySelector("#ssdInput").value);
    const preco = parseFloat(document.querySelector("#precoInput").value);

    if (!nome || !cpu || isNaN(ram) || ram <= 0 || isNaN(ssd) || ssd <= 0 || isNaN(preco) || preco <= 0) {
        alert("Por favor, preencha todos os campos corretamente!");
        return;
    }

    const data = {
        nome: nome,
        cpu: cpu,
        ram: ram,
        ssd: ssd,
        preco: preco
    };

    try {
        const response = await fetch("http://localhost:8080/modelos", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

    } catch (error) {
        console.error("Erro na requisição:", error);
        alert("Erro ao adicionar o modelo!");
    }

    fecharModal('add');
    await carregarModelos();
}
async function deletarModelo() {
    const idModelo = document.querySelector("#idInput").value;
    try {
        const response = await fetch(`http://localhost:8080/modelos/${idModelo}`, {
            method: "DELETE"
        });
    } catch (error) {
        console.error("Erro na requisição:", error);
    }

    fecharModal('delete');
    await carregarModelos();
};

async function editarModelo() {
    const idModelo = document.querySelector("#editIdInput").value;
    const nome = document.querySelector("#editNomeInput").value;
    const cpu = document.querySelector("#editCpuInput").value;
    const ram = parseInt(document.querySelector("#editRamInput").value);
    const ssd = parseInt(document.querySelector("#editSsdInput").value);
    const preco = parseFloat(document.querySelector("#editPrecoInput").value);

    if (!idModelo || !nome || !cpu || isNaN(ram) || ram <= 0 || isNaN(ssd) || ssd <= 0 || isNaN(preco) || preco <= 0) {
        alert("Por favor, preencha todos os campos corretamente!");
        return;
    }

    const data = {
        nome: nome,
        cpu: cpu,
        ram: ram,
        ssd: ssd,
        preco: preco
    };

    try {
        const response = await fetch(`http://localhost:8080/modelos/${idModelo}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

    } catch (error) {
        console.error("Erro na requisição:", error);
        alert("Erro ao editar o modelo!");
    }

    fecharModal('edit');
    await carregarModelos();
}


async function zerarInputs() {
    const todosInputs = document.querySelectorAll("input");

    todosInputs.forEach((input) => {
        input.value = "";
    })
}

function verificarUsuarioIsAdmin() {
    const usuarioLocal = localStorage.getItem("login");

    if (usuarioLocal != null) {
        const usuario = JSON.parse(usuarioLocal);

        const isAdmin = usuario.permissaoAdmin === 's';

        if (!isAdmin) {
            document.querySelector("#buttonsDiv").style.display = "none";
        }

    } else {
        window.location.href = "/pages/login";
    }
}


function logoutUsuario() {
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
