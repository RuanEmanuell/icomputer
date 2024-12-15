window.addEventListener('load', () => carregarModelos())

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

    carregarModelos(); 
    fecharModal('add');
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
    carregarModelos(); 
    fecharModal('delete');
};