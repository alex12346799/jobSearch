<style>
    body {
        background: linear-gradient(135deg, #6a11cb, #2575fc);
        font-family: 'Segoe UI', sans-serif;
        padding: 40px;
    }

    .custom-form {
        background: #ffffff;
        padding: 40px;
        border-radius: 16px;
        box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
        transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
        max-width: 800px;
        margin: auto;
    }

    .custom-form:hover {
        transform: scale(1.02);
        box-shadow: 0 15px 40px rgba(0, 0, 0, 0.2);
    }

    .custom-form h2 {
        text-align: center;
        margin-bottom: 30px;
        font-weight: 700;
        color: #333;
    }

    .custom-form .form-control {
        border-radius: 10px;
        border: 1px solid #ccc;
        transition: all 0.3s ease;
        padding: 12px 15px;
        font-size: 1.1rem;
    }
    .form-control{
        margin-left: auto;
    }


    .custom-form .form-control::placeholder {
        padding-left: 3px;
        color: #999;
    }

    .custom-form .form-control:focus {
        border-color: #6a11cb;
        box-shadow: 0 0 10px rgba(106, 17, 203, 0.3);
    }

    .custom-form label {
        font-weight: 600;
        margin-bottom: 8px;
        font-size: 1.1rem;
    }


    .custom-form .btn-submit {
        background: linear-gradient(90deg, #6a11cb, #2575fc);
        border: none;
        border-radius: 10px;
        padding: 10px 40px;
        font-size: 1rem;
        font-weight: bold;
        color: white;
        transition: all 0.3s ease;
        display: block;
        margin: 25px auto 0 auto;
        width: auto;
        min-width: 120px;
        text-align: center;
    }

    .custom-form .btn-submit:hover {
        background: linear-gradient(90deg, #2575fc, #6a11cb);
        transform: scale(1.05);
        box-shadow: 0 5px 15px rgba(37, 117, 252, 0.4);
    }
</style>

<form class="custom-form">
    <h2>Редактирование профиля</h2>
    <div class="row g-4">
        <div class="col-md-6">
            <label for="firstName" class="form-label">Имя</label>
            <input type="text" class="form-control" id="firstName" placeholder="Введите имя">
        </div>
        <div class="col-md-6">
            <label for="lastName" class="form-label">Фамилия</label>
            <input type="text" class="form-control" id="lastName" placeholder="Введите фамилию">
        </div>
        <div class="col-md-6">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" placeholder="Введите email">
        </div>
        <div class="col-md-3">
            <label for="age" class="form-label">Возраст</label>
            <input type="number" class="form-control" id="age" placeholder="18">
        </div>
        <div class="col-md-3">
            <label for="phone" class="form-label">Телефон</label>
            <input type="tel" class="form-control" id="phone" placeholder="+996 (___) __-__-__">
        </div>
        <div class="col-12">
            <button type="submit" class="btn-submit">Сохранить</button>
        </div>
    </div>
</form>
