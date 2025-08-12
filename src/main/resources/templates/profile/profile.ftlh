<style>
    .profile-container {
        max-width: 900px;
        background: white;
        border-radius: 16px;
        box-shadow: 0 12px 28px rgba(0,0,0,0.12);
        margin: 40px auto;
        display: flex;
        padding: 30px;
        gap: 40px;
        align-items: center;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        color: #333;
    }

    .profile-photo {
        flex-shrink: 0;
        width: 180px;
        height: 180px;
        border-radius: 50%;
        background: #ddd url('https://via.placeholder.com/180') center center no-repeat;
        background-size: cover;
        box-shadow: 0 4px 10px rgba(0,0,0,0.1);
    }

    .profile-info {
        flex-grow: 1;
    }

    .profile-info h1 {
        margin: 0 0 12px 0;
        font-size: 2rem;
        font-weight: 700;
    }

    .profile-info p {
        font-size: 1.1rem;
        margin: 6px 0;
    }

    .btn {
        display: inline-block;
        font-weight: 600;
        padding: 12px 28px;
        font-size: 1rem;
        border-radius: 10px;
        cursor: pointer;
        border: none;
        transition: background-color 0.3s ease;
        user-select: none;
    }

    .btn-edit {
        background-color: #6a11cb;
        color: white;
        margin-top: 20px;
    }
    .btn-edit:hover {
        background-color: #520da0;
    }

    .actions {
        margin-top: 40px;
        display: flex;
        gap: 20px;
    }

    .btn-create {
        background: linear-gradient(90deg, #2575fc, #6a11cb);
        color: white;
    }
    .btn-create:hover {
        background: linear-gradient(90deg, #6a11cb, #2575fc);
    }

    .btn-feedbacks {
        background-color: #e0e0e0;
        color: #333;
    }
    .btn-feedbacks:hover {
        background-color: #cacaca;
    }


</style>

<div class="profile-container">
    <div class="profile-photo" title="Фото профиля"></div>

    <div class="profile-info">
        <h1>Хван Алексей</h1>
        <p><strong>Фамилия:</strong> Хван</p>
        <p><strong>Возраст:</strong> 31</p>

        <button class="btn btn-edit">Редактировать</button>

        <div class="actions">
            <button class="btn btn-create">Создать резюме</button>
            <button class="btn btn-feedbacks">Отклики</button>
        </div>
    </div>
</div>
