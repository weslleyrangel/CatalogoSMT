function initComputadoresPage() {
    const pageConfig = {
        page: 'computadores',
        title: 'Gerenciar Computadores',
        contentHTML: `
            <div class="page-header">
                <button id="open-add-modal-btn" class="btn btn-primary">Adicionar Novo</button>
            </div>
            <div class="card">
                <div class="toolbar">
                    <input type="text" id="search-input" class="form-input" placeholder="Buscar por patrimônio, usuário...">
                </div>
                <div class="table-container">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th class="sortable" data-sort-key="patrimonio">Patrimônio <span class="sort-icon"></span></th>
                                <th class="sortable" data-sort-key="usuario">Usuário <span class="sort-icon"></span></th>
                                <th class="sortable" data-sort-key="setor">Setor <span class="sort-icon"></span></th>
                                <th class="sortable" data-sort-key="modelo">Modelo <span class="sort-icon"></span></th>
                                <th class="sortable" data-sort-key="status">Status <span class="sort-icon"></span></th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody id="computadores-table-body"></tbody>
                    </table>
                </div>
            </div>

            <div id="item-modal" class="modal">
                <div class="modal-content">
                    <div class="modal-header">
                        <h2 id="modal-title">Adicionar Computador</h2>
                        <span id="close-item-modal-btn" class="close-btn">&times;</span>
                    </div>
                    <div class="modal-body">
                        <div class="form-group" id="scan-button-container" style="margin-bottom: 2rem;">
                            <button type="button" id="scan-hardware-btn" class="btn btn-primary" style="width: 100%;">
                                 स्कैन Escanear Hardware Local
                            </button>
                        </div>
                        <form id="item-form">
                            <h3 style="margin-top:0;">Dados de Gestão</h3>
                            <div class="form-group"> <label for="patrimonio" class="form-label">Patrimônio *</label> <input type="text" id="patrimonio" name="patrimonio" class="form-input" required> </div>
                            <div class="form-group"> <label for="usuario" class="form-label">Usuário</label> <input type="text" id="usuario" name="usuario" class="form-input"> </div>
                            <div class="form-group"> <label for="setor" class="form-label">Setor *</label> <input type="text" id="setor" name="setor" class="form-input" required> </div>
                            <div class="form-group"> <label for="status" class="form-label">Status *</label> <select id="status" name="status" class="form-input" required> <option value="Ativo">Ativo</option> <option value="Manutenção">Manutenção</option> <option value="Inativo">Inativo</option> </select> </div>
                            <div class="form-group" id="maintenance-details-group" style="display: none;"> <label for="detalhesManutencao" id="maintenance-details-label" class="form-label">Detalhes da Manutenção</label> <textarea id="detalhesManutencao" name="detalhesManutencao" class="form-input" rows="3"></textarea> </div>
                            <hr style="margin: 2rem 0;">
                            <h3 style="margin-top:0;">Dados de Hardware (Escaneados)</h3>
                            <div class="form-group"> <label for="tipoDispositivo" class="form-label">Tipo de Dispositivo</label> <input type="text" id="tipoDispositivo" name="tipoDispositivo" class="form-input" readonly> </div>
                            <div class="form-group"> <label for="fabricante" class="form-label">Fabricante</label> <input type="text" id="fabricante" name="fabricante" class="form-input"> </div>
                            <div class="form-group"> <label for="modelo" class="form-label">Modelo</label> <input type="text" id="modelo" name="modelo" class="form-input"> </div>
                            <div class="form-group"> <label for="placaMae" class="form-label">Placa Mãe</label> <input type="text" id="placaMae" name="placaMae" class="form-input" readonly> </div>
                            <div class="form-group"> <label for="bios" class="form-label">BIOS</label> <input type="text" id="bios" name="bios" class="form-input" readonly> </div>
                            <div class="form-group"> <label for="processador" class="form-label">Processador</label> <input type="text" id="processador" name="processador" class="form-input"> </div>
                            <div class="form-group"> <label for="ram" class="form-label">Memória RAM (Total)</label> <input type="text" id="ram" name="ram" class="form-input"> </div>
                            <div class="form-group"> <label for="memoriaDetalhes" class="form-label">Detalhes da Memória</label> <input type="text" id="memoriaDetalhes" name="memoriaDetalhes" class="form-input" readonly> </div>
                            <div class="form-group"> <label for="armazenamento" class="form-label">Armazenamento</label> <input type="text" id="armazenamento" name="armazenamento" class="form-input" readonly> </div>
                            <div class="form-group"> <label for="os" class="form-label">Sistema Operacional</label> <input type="text" id="os" name="os" class="form-input"> </div>
                            <div class="modal-footer"> <button type="submit" class="btn btn-primary">Salvar</button> </div>
                        </form>
                    </div>
                </div>
            </div>

            <div id="history-modal" class="modal">
                <div class="modal-content" style="max-width: 700px;">
                    <div class="modal-header">
                        <h2 id="history-modal-title">Histórico de Manutenção</h2>
                        <span id="close-history-modal-btn" class="close-btn">&times;</span>
                    </div>
                    <div class="modal-body" id="history-modal-body"></div>
                </div>
            </div>

            <div id="delete-confirm-modal" class="modal">
                <div class="modal-content">
                    <div class="modal-header">
                        <h2>Confirmar Exclusão</h2>
                        <span id="close-delete-modal-btn" class="close-btn">&times;</span>
                    </div>
                    <div class="modal-body">
                        <p>Você tem certeza que deseja excluir o computador com patrimônio <strong id="delete-item-id"></strong>?</p>
                    </div>
                    <div class="modal-footer">
                        <button id="cancel-delete-btn" class="btn btn-secondary">Cancelar</button>
                        <button id="confirm-delete-btn" class="btn btn-danger">Confirmar Exclusão</button>
                    </div>
                </div>
            </div>
        `
    };
    loadLayout(pageConfig);
    
    const pageContext = {
        currentEditingId: null,
        deleteTargetId: null,
        computadores: [],
        sortKey: 'patrimonio',
        sortDirection: 'asc',
        currentStatus: null
    };

    loadComputadoresData(pageContext);
    setupComputadorEventListeners(pageContext);
}

function addClickListener(id, handler) {
    const element = document.getElementById(id);
    if (element) {
        element.onclick = handler;
    } else {
        console.error(`Erro de setup: Elemento com o id "${id}" não foi encontrado no DOM.`);
    }
}

function setupComputadorEventListeners(context) {
    const itemModal = document.getElementById('item-modal');
    const deleteModal = document.getElementById('delete-confirm-modal');
    const historyModal = document.getElementById('history-modal');
    const itemForm = document.getElementById('item-form');
    const searchInput = document.getElementById('search-input');
    const tableBody = document.getElementById('computadores-table-body');
    const tableHeader = document.querySelector('.data-table thead');
    const statusDropdown = document.getElementById('status');

    addClickListener('open-add-modal-btn', () => openComputadorModal(null, context));
    addClickListener('close-item-modal-btn', () => { if (itemModal) itemModal.style.display = 'none'; });
    addClickListener('close-delete-modal-btn', () => { if (deleteModal) deleteModal.style.display = 'none'; });
    addClickListener('cancel-delete-btn', () => { if (deleteModal) deleteModal.style.display = 'none'; });
    addClickListener('confirm-delete-btn', () => handleConfirmDelete(context));
    addClickListener('close-history-modal-btn', () => { if (historyModal) historyModal.style.display = 'none'; });
    addClickListener('scan-hardware-btn', handleScanComputer);

    if (itemForm) {
        itemForm.onsubmit = (event) => handleComputadorFormSubmit(event, context);
    } else {
        console.error('Erro de setup: Elemento com o id "item-form" não foi encontrado.');
    }
    
    if (statusDropdown) {
        statusDropdown.addEventListener('change', (event) => {
            handleStatusChange(event.target.value, context.currentStatus);
        });
    } else {
        console.error('Erro de setup: Elemento com o id "status" não foi encontrado.');
    }

    if (tableBody) {
        tableBody.addEventListener('click', (event) => {
            const target = event.target;
            if (target.matches('.btn-edit')) {
                openComputadorModal(target.dataset.id, context);
            } else if (target.matches('.btn-delete')) {
                openDeleteComputadorModal(target.dataset.id, context);
            } else if (target.matches('.btn-history')) {
                openHistoryModal(target.dataset.id);
            }
        });
    }

    if (tableHeader) {
        tableHeader.addEventListener('click', (event) => {
            const headerCell = event.target.closest('th.sortable');
            if (!headerCell) return;
            const newSortKey = headerCell.dataset.sortKey;
            if (context.sortKey === newSortKey) {
                context.sortDirection = context.sortDirection === 'asc' ? 'desc' : 'asc';
            } else {
                context.sortKey = newSortKey;
                context.sortDirection = 'asc';
            }
            renderComputadoresTable(context);
        });
    }
    
    if (searchInput) {
        let debounceTimer;
        searchInput.oninput = () => {
            clearTimeout(debounceTimer);
            debounceTimer = setTimeout(() => {
                loadComputadoresData(context, searchInput.value);
            }, 300);
        };
    }

    window.onclick = (event) => {
        if (event.target === itemModal) itemModal.style.display = 'none';
        if (event.target === deleteModal) deleteModal.style.display = 'none';
        if (event.target === historyModal) historyModal.style.display = 'none';
    };
}

async function openComputadorModal(patrimonio = null, context) {
    const modal = document.getElementById('item-modal');
    const title = document.getElementById('modal-title');
    const form = document.getElementById('item-form');
    const patrimonioInput = document.getElementById('patrimonio');
    const scanButtonContainer = document.getElementById('scan-button-container');
    
    form.reset();
    context.currentEditingId = patrimonio;

    if (patrimonio) {
        title.textContent = 'Editar Computador';
        patrimonioInput.disabled = true;
        scanButtonContainer.style.display = 'none';
        try {
            const item = await request(`/computadores/${patrimonio}`, 'GET');
            Object.keys(item).forEach(key => {
                const element = form.elements[key];
                if (element) element.value = item[key];
            });
            context.currentStatus = item.status;
        } catch (error) {
            alert('Falha ao carregar dados do computador.');
            console.error(error);
        }
    } else {
        title.textContent = 'Adicionar Computador';
        patrimonioInput.disabled = false;
        scanButtonContainer.style.display = 'block';
        context.currentStatus = 'Ativo';
    }
    
    handleStatusChange(document.getElementById('status').value, context.currentStatus);
    modal.style.display = 'block';
}

async function handleScanComputer() {
    const scanButton = document.getElementById('scan-hardware-btn');
    const originalText = scanButton.innerHTML;
    const form = document.getElementById('item-form');

    try {
        scanButton.disabled = true;
        scanButton.innerHTML = 'Escaneando...';
        const hardwareInfo = await request('/computadores/local-info', 'GET');
        
        if (hardwareInfo) {
            form.elements['tipoDispositivo'].value = hardwareInfo.tipoDispositivo || '';
            form.elements['fabricante'].value = hardwareInfo.fabricante || '';
            form.elements['modelo'].value = hardwareInfo.modelo || '';
            form.elements['placaMae'].value = hardwareInfo.placaMae || '';
            form.elements['bios'].value = hardwareInfo.bios || '';
            form.elements['processador'].value = hardwareInfo.processador || '';
            form.elements['ram'].value = hardwareInfo.ram || '';
            form.elements['memoriaDetalhes'].value = hardwareInfo.memoriaDetalhes || '';
            form.elements['armazenamento'].value = hardwareInfo.armazenamentoDetalhes || '';
            form.elements['os'].value = hardwareInfo.os || '';
        }
        alert('Hardware escaneado com sucesso! Preencha os campos de gestão.');
    } catch (error) {
        alert(`Falha ao escanear o hardware: ${error.message}`);
        console.error('Erro no escaneamento:', error);
    } finally {
        scanButton.disabled = false;
        scanButton.innerHTML = originalText;
    }
}

function handleStatusChange(newStatus, oldStatus) {
    const detailsGroup = document.getElementById('maintenance-details-group');
    const detailsLabel = document.getElementById('maintenance-details-label');
    const detailsTextarea = document.getElementById('detalhesManutencao');

    if (newStatus === 'Manutenção' && oldStatus !== 'Manutenção') {
        detailsLabel.textContent = 'Descrição do Problema *';
        detailsTextarea.value = '';
        detailsTextarea.required = true;
        detailsGroup.style.display = 'block';
    } else if (newStatus !== 'Manutenção' && oldStatus === 'Manutenção') {
        detailsLabel.textContent = 'Solução Aplicada / Peças Trocadas *';
        detailsTextarea.value = '';
        detailsTextarea.required = true;
        detailsGroup.style.display = 'block';
    } else {
        detailsTextarea.required = false;
        detailsGroup.style.display = 'none';
    }
}

async function openHistoryModal(patrimonio) {
    const modal = document.getElementById('history-modal');
    const modalBody = document.getElementById('history-modal-body');
    document.getElementById('history-modal-title').textContent = `Histórico de Manutenção - ${patrimonio}`;
    modalBody.innerHTML = '<p>Carregando histórico...</p>';
    modal.style.display = 'block';

    try {
        const historico = await request(`/computadores/${patrimonio}/historico`, 'GET');
        if (historico.length === 0) {
            modalBody.innerHTML = '<p>Nenhum registro de manutenção encontrado para este computador.</p>';
            return;
        }

        let content = '';
        historico.forEach(item => {
            const dataEntrada = new Date(item.dataEntrada).toLocaleString('pt-BR');
            const dataSaida = item.dataSaida ? new Date(item.dataSaida).toLocaleString('pt-BR') : 'Em andamento';
            const detalhesFormatados = item.detalhes ? item.detalhes.replace(/\n/g, '<br>') : 'Nenhum detalhe fornecido.';
            content += `
                <div class="card" style="margin-bottom: 1rem; border-left: 4px solid var(--primary-color);">
                    <p><strong>Entrada em Manutenção:</strong> ${dataEntrada}</p>
                    <p><strong>Retorno à Atividade:</strong> ${dataSaida}</p>
                    <hr>
                    <p><strong>Detalhes Registrados:</strong></p>
                    <div style="white-space: pre-wrap; background-color: #f4f7f6; padding: 10px; border-radius: 4px;">${detalhesFormatados}</div>
                </div>
            `;
        });
        modalBody.innerHTML = content;
    } catch (error) {
        modalBody.innerHTML = '<p style="color: red;">Falha ao carregar o histórico.</p>';
        console.error('Erro ao buscar histórico:', error);
    }
}

async function handleComputadorFormSubmit(event, context) {
    event.preventDefault();
    const data = Object.fromEntries(new FormData(event.target).entries());

    try {
        const method = context.currentEditingId ? 'PUT' : 'POST';
        const endpoint = context.currentEditingId ? `/computadores/${context.currentEditingId}` : '/computadores';
        await request(endpoint, method, data);
        document.getElementById('item-modal').style.display = 'none';
        loadComputadoresData(context);
    } catch (error) {
        alert(`Erro ao salvar computador: ${error.message}`);
        console.error('Falha ao salvar:', error);
    }
}

function renderComputadoresTable(context) {
    const { computadores, sortKey, sortDirection } = context;
    const tableBody = document.getElementById('computadores-table-body');
    if (!tableBody) return;

    const sortedData = [...computadores].sort((a, b) => {
        const valA = a[sortKey] || '';
        const valB = b[sortKey] || '';
        if (valA < valB) return sortDirection === 'asc' ? -1 : 1;
        if (valA > valB) return sortDirection === 'asc' ? 1 : -1;
        return 0;
    });

    tableBody.innerHTML = '';
    if (sortedData.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="6" style="text-align: center;">Nenhum computador encontrado.</td></tr>`;
        return;
    }

    sortedData.forEach(comp => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${comp.patrimonio}</td>
            <td>${comp.usuario || 'N/A'}</td>
            <td>${comp.setor}</td>
            <td>${comp.modelo || 'N/A'}</td>
            <td><span class="status-badge status-${comp.status.toLowerCase()}">${comp.status}</span></td>
            <td class="actions-cell">
                <button class="btn btn-secondary btn-sm btn-edit" data-id="${comp.patrimonio}">Editar</button>
                <button class="btn btn-secondary btn-sm btn-history" data-id="${comp.patrimonio}">Histórico</button>
                <button class="btn btn-danger btn-sm btn-delete" data-id="${comp.patrimonio}">Excluir</button>
            </td>
        `;
        tableBody.appendChild(row);
    });

    document.querySelectorAll('.data-table th.sortable .sort-icon').forEach(icon => icon.textContent = '');
    const activeHeader = document.querySelector(`.data-table th[data-sort-key="${sortKey}"] .sort-icon`);
    if (activeHeader) {
        activeHeader.textContent = sortDirection === 'asc' ? '▲' : '▼';
    }
}

async function loadComputadoresData(context, searchTerm = '') {
    try {
        const endpoint = searchTerm ? `/computadores/filtrar?termo=${encodeURIComponent(searchTerm)}` : '/computadores';
        context.computadores = await request(endpoint, 'GET');
        renderComputadoresTable(context);
    } catch (error) {
        console.error('Erro ao carregar computadores:', error);
        const tableBody = document.getElementById('computadores-table-body');
        if (tableBody) {
            tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center; color: red;">Falha ao carregar dados.</td></tr>';
        }
    }
}

function openDeleteComputadorModal(patrimonio, context) {
    const deleteModal = document.getElementById('delete-confirm-modal');
    document.getElementById('delete-item-id').textContent = patrimonio;
    context.deleteTargetId = patrimonio;
    deleteModal.style.display = 'block';
}

async function handleConfirmDelete(context) {
    if (!context.deleteTargetId) return;
    try {
        await request(`/computadores/${context.deleteTargetId}`, 'DELETE');
        document.getElementById('delete-confirm-modal').style.display = 'none';
        loadComputadoresData(context);
    } catch (error) {
        alert(`Falha ao excluir o computador: ${error.message}`);
        console.error('Erro ao excluir:', error);
    }
}