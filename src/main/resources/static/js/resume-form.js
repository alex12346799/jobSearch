let workIndex = 1;
let eduIndex = 1;



function addWorkExperience() {
    const container = document.getElementById('experienceContainer');
    if (!container) return;

    const div = document.createElement('div');
    div.classList.add('mb-2');

    div.innerHTML = `
        <input type="text" name="workExperienceInfoList[${workIndex}].companyName" placeholder="Компания" class="form-control mb-2" />
        <input type="text" name="workExperienceInfoList[${workIndex}].position" placeholder="Должность" class="form-control mb-2" />
                <input type="text" name="workExperienceInfoList[${workIndex}].responsibilities" placeholder="Обязаности" class="form-control mb-2" />
        <input type="number" name="workExperienceInfoList[${workIndex}].years" placeholder="Годы" class="form-control mb-2" />
        <button type="button" class="btn btn-danger btn-sm mb-2">Удалить</button>
    `;


    div.querySelector('button').addEventListener('click', () => div.remove());

    container.appendChild(div);
    workIndex++;
}

function addEducation() {
    const container = document.getElementById('educationContainer');
    if (!container) return;

    const div = document.createElement('div');
    div.classList.add('mb-2');

    div.innerHTML = `
        <input type="text" name="educationInfoList[${eduIndex}].institution" placeholder="Учебное заведение" class="form-control mb-2" />
        <input type="text" name="educationInfoList[${eduIndex}].program" placeholder="Программа" class="form-control mb-2" />
        <input type="text" name="educationInfoList[${eduIndex}].degree" placeholder="Степень" class="form-control mb-2" />
        <input type="date" name="educationInfoList[${eduIndex}].startDate" class="form-control mb-2" />
        <input type="date" name="educationInfoList[${eduIndex}].endDate" class="form-control mb-2" />
        <button type="button" class="btn btn-danger btn-sm mb-2">Удалить</button>
    `;

    div.querySelector('button').addEventListener('click', () => div.remove());

    container.appendChild(div);
    eduIndex++;
}
