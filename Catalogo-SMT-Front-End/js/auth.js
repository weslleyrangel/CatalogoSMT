document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('login-form');
    const logoutButton = document.getElementById('logout-button');
    const forgotPasswordLink = document.getElementById('forgot-password-link');
    const recoveryModal = document.getElementById('recovery-modal');
    const closeRecoveryModalBtn = document.getElementById('close-recovery-modal-btn');
    const recoveryForm = document.getElementById('recovery-form');

    if (loginForm) {
        loginForm.addEventListener('submit', handleLogin);
    }

    if (!window.location.pathname.endsWith('login.html')) {
        checkAuthentication();
    }

    if (logoutButton) {
        logoutButton.addEventListener('click', handleLogout);
    }

    if (forgotPasswordLink) {
        forgotPasswordLink.onclick = (e) => {
            e.preventDefault();
            if(recoveryModal) recoveryModal.style.display = 'block';
        };
    }

    if (closeRecoveryModalBtn) {
        closeRecoveryModalBtn.onclick = () => {
            if(recoveryModal) recoveryModal.style.display = 'none';
        };
    }

    if (recoveryForm) {
        recoveryForm.onsubmit = async (e) => {
            e.preventDefault();
            const email = document.getElementById('recovery-email').value;
            const recoveryMessageDiv = document.getElementById('recovery-message');

            try {
                const data = await request('/auth/recuperar-senha', 'POST', { email });
                recoveryMessageDiv.textContent = data.mensagem;
                recoveryMessageDiv.style.backgroundColor = '#d4edda';
                recoveryMessageDiv.style.color = '#155724';
                recoveryMessageDiv.style.display = 'block';
            } catch (error) {
                recoveryMessageDiv.textContent = error.message || 'Erro ao processar a solicitação.';
                recoveryMessageDiv.style.backgroundColor = '#f8d7da';
                recoveryMessageDiv.style.color = '#721c24';
                recoveryMessageDiv.style.display = 'block';
            }
        };
    }
    
    window.onclick = (event) => {
        if (event.target == recoveryModal) {
            recoveryModal.style.display = "none";
        }
    };
});

function checkAuthentication() {
    const user = sessionStorage.getItem('user');
    if (!user) {
        window.location.href = 'login.html';
    }
}

async function handleLogin(event) {
    event.preventDefault();
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    const errorMessageDiv = document.getElementById('error-message');

    try {
        const data = await request('/auth/login', 'POST', { email, senha });

        if (data.sucesso) {
            sessionStorage.setItem('user', JSON.stringify({ nome: data.usuario, perfil: data.perfil }));
            window.location.href = 'dashboard.html';
        } else {
            errorMessageDiv.textContent = data.mensagem || 'Email ou senha inválidos.';
            errorMessageDiv.style.display = 'block';
        }
    } catch (error) {
        errorMessageDiv.textContent = error.message || 'Erro ao conectar com o servidor. Tente novamente.';
        errorMessageDiv.style.display = 'block';
    }
}

async function handleLogout() {
    try {
        await request('/auth/logout', 'POST');
    } catch (error) {
        console.error('Erro ao fazer logout no backend:', error);
    } finally {
        sessionStorage.clear();
        window.location.href = 'login.html';
    }
}