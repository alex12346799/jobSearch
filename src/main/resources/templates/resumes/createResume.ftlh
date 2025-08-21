
<#import "../layout.ftlh" as main>
<@main.layout ; spring>

    <div class="container mt-4 p-4 border rounded" style="max-width:600px;">
        <h3 class="text-danger mb-4 text-center">Создание резюме:</h3>

        <form action="/resumes/create" method="post">

            <#if _csrf??>
                <input
                        type="hidden"
                        name="${(_csrf.parameterName)!'csrf-param-name'}"
                        value="${(_csrf.token)!'csrf-token'}"/>
            </#if>

            <input type="hidden" name="applicantId" value="${applicantId}">

            <div class="mb-3">
                <label for="name" class="form-label">Название резюме:</label>
                <input type="text" id="name" name="name" class="form-control" placeholder="Введите название" required>
            </div>

            <div class="mb-3">
                <label>Категория:
                    <select name="categoryId" required>
                        <option value="">Выберите категорию</option>
                        <#list categories as category>
                            <option value="${category.id}" <#if resume?has_content && resume.categoryId?? && (resume.categoryId?string == category.id?string)>selected</#if>>

                                ${category.name}
                            </option>
                        </#list>
                    </select>
                </label>
            </div>

            <div class="mb-3">
                <label for="salary" class="form-label">Ожидаемый уровень оклада:</label>
                <input type="number" id="salary" name="salary" class="form-control" placeholder="Введите оклад" required>
            </div>

            <div class="mb-3">
                <label>Опыт работы:</label>
                <div class="mb-2">
<#--                    <input type="text" name="workExperienceInfoList[0].companyName" class="form-control mb-2" placeholder="Компания" required>-->
<#--                    <input type="text" name="workExperienceInfoList[0].position" class="form-control mb-2" placeholder="Должность" required>-->
<#--                    <input type="number" name="workExperienceInfoList[0].years" class="form-control" placeholder="Годы" required>-->
                    <div class="form-outline mb-2">
                        <label class="form-label" for="companyName">Компания</label>
                        <@spring.formInput "workExperienceDto.companyName" 'id="companyName" class="form-control"' />
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>

                    <input type="text" name="workExperienceInfoList[0].position" class="form-control mb-2" placeholder="Должность" required>
                                        <input type="number" name="workExperienceInfoList[0].years" class="form-control" placeholder="Годы" required>

<#--                        <div data-mdb-input-init class="form-outline mb-4">-->
<#--                            <label class="form-label" for="form3Example3cg">Your Email</label>-->
<#--                            <@spring.formInput "userDto.email" 'id="form3Example3cg" class="form-control form-control-lg"' />-->
<#--                            <@spring.showErrors "<br>" "error text-danger" />-->
<#--                        </div>-->


                    </div>
                </div>



            <div class="mb-3">
                <label>Образование:</label>
                <div class="mb-2">
                    <input type="text" name="educationInfoList[0].institution" class="form-control mb-2" placeholder="Учебное заведение" required>
                    <input type="text" name="educationInfoList[0].program" class="form-control mb-2" placeholder="Программа" required>
                    <input type="date" name="educationInfoList[0].startDate" class="form-control mb-2" required>
                    <input type="date" name="educationInfoList[0].endDate" class="form-control mb-2" required>
                    <input type="text" name="educationInfoList[0].degree" class="form-control" placeholder="Степень" required>
                </div>

            </div>

            <div class="mb-3">
                <label>Социальные ссылки:</label>
                <input type="text" name="socialLinkRequestDto.telegram" class="form-control mb-2" placeholder="Telegram ссылка">
                <input type="text" name="socialLinkRequestDto.facebook" class="form-control mb-2" placeholder="Facebook ссылка">
                <input type="text" name="socialLinkRequestDto.linkedin" class="form-control" placeholder="LinkedIn ссылка">
            </div>



            <div class="form-check mb-3">
                <input type="checkbox" id="isActive" name="isActive" class="form-check-input" checked>
                <label for="isActive" class="form-check-label">Активно</label>
            </div>

            <button type="submit" class="btn btn-success w-100">Опубликовать</button>
        </form>
    </div>

</@main.layout>
