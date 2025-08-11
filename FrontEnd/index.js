function carregarUsuarios() {
  const token = localStorage.getItem("token");

  if (!token) {
    console.log("Usuário não autenticado");
    return;
  }

  fetch("http://localhost:8080/usuarios", {
    headers: {
      Authorization: "Bearer " + token,
    },
  })
    .then((data) => data.json())
    .then((response) => {
      console.log(response);
      const lista = document.getElementById("usuarios");
      for (let usuario of response) {
        const li = document.createElement("li");
        li.innerText = usuario.email;
        lista.appendChild(li);
      }
    })
    .catch((error) => {
      console.log(error);
      localStorage.removeItem("token");
    });
}

function realizarLogin() {
  const inputEmail = document.getElementById("email");
  const inputPassword = document.getElementById("password");

  const email = inputEmail.value;
  const password = inputPassword.value;

  console.log(email, password);

  if (!email || !password) {
    alert("Digite o email e a senha");
    return;
  }

  fetch("http://localhost:8080/auth/login", {
    method: "POST",
    body: JSON.stringify({
      email,
      password,
    }),
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((data) => data.json())
    .then((response) => {
      console.log(response);
      localStorage.setItem("token", response.token);
      carregarUsuarios();
    })
    .catch((error) => {
      console.log(error);
      alert("Usuario ou senha incorretos");
    });
}

function configurarEventos() {
  const botaoLogar = document.getElementById("logar");
  botaoLogar.addEventListener("click", realizarLogin);

  carregarUsuarios();
}

window.addEventListener("load", configurarEventos);
