const API_BASE_URL = "http://localhost:8080/api";

/**
 * Função genérica para fazer requisições à API.
 * @param {string} endpoint O endpoint da API (ex: /computadores)
 * @param {string} method O método HTTP (GET, POST, PUT, DELETE)
 * @param {object} [body=null] O corpo da requisição para POST/PUT
 * @returns {Promise<any>} A resposta da API em JSON
 */
async function request(endpoint, method, body = null) {
  const options = {
    method,
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include",
  };

  if (body) {
    options.body = JSON.stringify(body);
  }

  try {
    const response = await fetch(`${API_BASE_URL}${endpoint}`, options);

    if (!response.ok) {
      let errorMessage = `Erro na requisição: ${response.statusText}`;
      try {
        const errorData = await response.json();
        errorMessage = errorData.mensagem || errorData.error || errorMessage;
      } catch (e) {}
      throw new Error(errorMessage);
    }

    if (response.status === 204) {
      return {};
    }

    return response.json();
  } catch (error) {
    console.error(`Falha na API: ${method} ${endpoint}`, error);
    throw error;
  }
}
