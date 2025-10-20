document.addEventListener('DOMContentLoaded', () => {
    const path = window.location.pathname;

    if (path.endsWith('/') || path.endsWith('dashboard.html')) {
        initDashboardPage();
    } else if (path.endsWith('computadores.html')) {
        initComputadoresPage();
    } else if (path.endsWith('impressoras.html')) {
        initImpressorasPage();
    } else if (path.endsWith('usuarios.html')) {
        initUsuariosPage();
    }
});
function initDashboardPage() {
    const pageConfig = {
        page: 'dashboard',
        title: 'Dashboard',
        contentHTML: `
            <div class="stats-grid">
                <div class="card stat-card">
                    <h3>Total de Computadores</h3>
                    <p id="total-computadores">0</p>
                </div>
                <div class="card stat-card">
                    <h3>Total de Impressoras</h3>
                    <p id="total-impressoras">0</p>
                </div>
                <div class="card stat-card">
                    <h3>Em Manutenção</h3>
                    <p id="total-manutencao">0</p>
                </div>
            </div>
            <div class="card mt-xl">
                <h2>Atividade Recente</h2>
                <div id="recent-activity-container">
                    <p>Nenhuma atividade recente para mostrar.</p>
                </div>
            </div>
        `
    };
    loadLayout(pageConfig);
    loadDashboardStats();
    loadRecentActivity(); 
}

async function loadDashboardStats() {
    try {
        const [statsComputadores, statsImpressoras] = await Promise.all([
            request('/computadores/estatisticas', 'GET'),
            request('/impressoras/estatisticas', 'GET')
        ]);

        document.getElementById('total-computadores').textContent = statsComputadores.total ?? 0;
        document.getElementById('total-impressoras').textContent = statsImpressoras.total ?? 0;
        document.getElementById('total-manutencao').textContent = statsComputadores.manutencao ?? 0;

    } catch (error) {
        console.error('Erro ao carregar dados do dashboard:', error);
    }
}


async function loadRecentActivity() {
    const container = document.getElementById('recent-activity-container');
    try {
        const atividades = await request('/atividades/recentes', 'GET');

        if (atividades.length === 0) {
            container.innerHTML = '<p>Nenhuma atividade recente para mostrar.</p>';
            return;
        }

        const activityList = atividades.map(act => {
            const data = new Date(act.data).toLocaleString('pt-BR');
            const descricaoFormatada = act.descricao.replace(/\n/g, '<br>');
            
            return `
                <div style="border-bottom: 1px solid #eee; padding: 12px 5px; display: flex; justify-content: space-between; align-items: flex-start; gap: 15px;">
                    <div>
                        <strong style="color: var(--primary-color);">${act.patrimonio}</strong>
                        <div style="font-size: 0.9em; margin-top: 5px; white-space: pre-wrap;">${descricaoFormatada}</div>
                    </div>
                    <span style="color: #6c757d; font-size: 0.9em; text-align: right; white-space: nowrap;">${data}</span>
                </div>
            `;
        }).join('');

        container.innerHTML = activityList;

    } catch (error) {
        container.innerHTML = '<p style="color: red;">Falha ao carregar atividades recentes.</p>';
        console.error('Erro ao carregar atividades:', error);
    }
}

function initDashboardPage() {
    const pageConfig = {
        page: 'dashboard',
        title: 'Dashboard',
        contentHTML: `
            <div class="stats-grid">
                <div class="card stat-card">
                    <h3>Total de Computadores</h3>
                    <p id="total-computadores">0</p>
                </div>
                <div class="card stat-card">
                    <h3>Total de Impressoras</h3>
                    <p id="total-impressoras">0</p>
                </div>
                <div class="card stat-card">
                    <h3>Em Manutenção</h3>
                    <p id="total-manutencao">0</p>
                </div>
            </div>
            <div class="card mt-xl">
                <h2>Atividade Recente</h2>
                <div id="recent-activity-container">
                    <p>Nenhuma atividade recente para mostrar.</p>
                </div>
            </div>
        `
    };
    loadLayout(pageConfig);
    loadDashboardStats();
    loadRecentActivity();
}

async function loadDashboardStats() {
    try {
        const [statsComputadores, statsImpressoras] = await Promise.all([
            request('/computadores/estatisticas', 'GET'),
            request('/impressoras/estatisticas', 'GET')
        ]);

        document.getElementById('total-computadores').textContent = statsComputadores.total ?? 0;
        document.getElementById('total-impressoras').textContent = statsImpressoras.total ?? 0;
        document.getElementById('total-manutencao').textContent = statsComputadores.manutencao ?? 0;

    } catch (error) {
        console.error('Erro ao carregar dados do dashboard:', error);
    }
}

async function loadRecentActivity() {
    const container = document.getElementById('recent-activity-container');
    try {
        const atividades = await request('/atividades/recentes', 'GET');

        if (atividades.length === 0) {
            container.innerHTML = '<p>Nenhuma atividade recente para mostrar.</p>';
            return;
        }

        const activityList = atividades.map(act => {
            const data = new Date(act.data).toLocaleString('pt-BR');
            return `
                <div style="border-bottom: 1px solid #eee; padding: 10px 0;">
                    <strong>${act.patrimonio}:</strong> ${act.descricao} 
                    <span style="float: right; color: #6c757d; font-size: 0.9em;">${data}</span>
                </div>
            `;
        }).join('');

        container.innerHTML = activityList;

    } catch (error) {
        container.innerHTML = '<p style="color: red;">Falha ao carregar atividades recentes.</p>';
        console.error('Erro ao carregar atividades:', error);
    }
}