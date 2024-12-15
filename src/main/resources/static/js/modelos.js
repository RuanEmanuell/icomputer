function abrirModal(option){
    const dialog = document.querySelector("#" + option + "Modal");
    dialog.showModal();
}

function fecharModal(option){
    const dialog = document.querySelector("#" + option + "Modal");
    dialog.close();
}

async function carregarModelos(){
    htmx.ajax('GET', 'http://localhost:8080/pages/fragment/lista-modelos', {
        target: '#modelos-container'
    });
}

async function adicionarModelo() {
    const data = {
        nome: document.querySelector("#nomeInput").value,
        cpu: document.querySelector("#cpuInput").value,
        ram: parseInt(document.querySelector("#ramInput").value),
        ssd: parseInt(document.querySelector("#ssdInput").value),
        preco: parseFloat(document.querySelector("#precoInput").value)
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
    const data = {
        nome: document.querySelector("#editNomeInput").value,
        cpu: document.querySelector("#editCpuInput").value, 
        ram: parseInt(document.querySelector("#editRamInput").value), 
        ssd: parseInt(document.querySelector("#editSsdInput").value),
        preco: parseFloat(document.querySelector("#editPrecoInput").value) 
    };

    try {
        const response = await fetch(`http://localhost:8080/modelos/${idModelo}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

    } catch (error) {
        console.error("Erro na requisição:", error);
    }

    fecharModal('edit');
    await carregarModelos(); 
}
