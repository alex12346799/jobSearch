
<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout; spring>

    <div class="container mt-4 p-4 border rounded" style="max-width:600px;">
        <h3 class="text-danger mb-4 text-center"><@spring.message "createResume.creating_a_resume"/>:</h3>

        <form action="/resumes/create" method="post">

            <#if _csrf??>
                <input
                        type="hidden"
                        name="${(_csrf.parameterName)!'csrf-param-name'}"
                        value="${(_csrf.token)!'csrf-token'}"/>
            </#if>

            <input type="hidden" name="applicantId" value="${applicantId}">


            <div data-mdb-input-init class="form-outline mb-4">
                <label class="form-label" for="form3Example4cg"><@spring.message "createResume.resume_title"/>:</label>
                <input type="text" class="form-control mb-2" id="form3Example4cg" name="name">
                <#--                <@spring.formInput "name" 'id="form3Example4cg" class="form-control form-control-lg"' />-->

                <#--                <@spring.showErrors "name" "error text-danger" />-->
            </div>


            <div class="mb-3">
                <label>Категория</label>
                <select name="categoryId" class="form-select" required>
                    <option value="">выберите категорию</option>
                    <#list categories as category>
                        <option value="${category.id}">
                            <#--                                <#if categoryId?? && categoryId == category.id>selected</#if>>-->
                            ${category.name}
                        </option>
                    </#list>
                </select>


            </div>


            <div class="mb-3">
                <label class="form-label" for="form3Example4cg1"><@spring.message "createResume.expected_salary_level"/>
                    :</label>
                <input type="number" class="form-control form-control-lg mb-2" id="form3Example4cg" name="salary">
                <#--                <@spring.formInput "salary" 'id="form3Example4cg1" class="form-control form-control-lg"' />-->
                <#--                <@spring.showErrors "salary"  "error text-danger" />-->
            </div>


            <div class="mb-3">
                <label>Опыт работы:</label>
                <div id="experienceContainer">
                    <div class="mb-2">
                        <input type="text" name="WorkExperienceInfoList[0].companyName" placeholder="Компания"
                               class="form-control mb-2"/>
                        <input type="text" name="WorkExperienceInfoList[0].position" placeholder="Должность"
                               class="form-control mb-2"/>
                        <input type="text" name="WorkExperienceInfoList[0].responsibilities" placeholder="Обязаности"
                               class="form-control mb-2"/>
<#--                        <input type="number" name="WorkExperienceInfoList[0].years" placeholder="Годы"-->
<#--                               class="form-control mb-2"/>-->


                        <input type="date" name="WorkExperienceInfoList[0].startDate" class="form-control mb-2"/>
                        <input type="date" name="WorkExperienceInfoList[0].endDate" class="form-control mb-2"/>


                    </div>
                </div>
                <button type="button" class="btn btn-primary btn-sm" onclick="addWorkExperience()">+ Добавить опыт
                </button>
            </div>

            <div class="mb-3">
                <label>Образование:</label>
                <div id="educationContainer">
                    <div class="mb-2">
                        <input type="text" name="EducationInfoList[0].institution" placeholder="Учебное заведение"
                               class="form-control mb-2"/>
                        <input type="text" name="EducationInfoList[0].program" placeholder="Программа"
                               class="form-control mb-2"/>
                        <input type="text" name="EducationInfoList[0].degree" placeholder="Степень"
                               class="form-control mb-2"/>
                        <input type="date" name="EducationInfoList[0].startDate" class="form-control mb-2"/>
                        <input type="date" name="EducationInfoList[0].endDate" class="form-control mb-2"/>
                    </div>
                </div>
                <button type="button" class="btn btn-primary btn-sm" onclick="addEducation()">+ Добавить образование
                </button>
            </div>

            <input type="text" class="form-control mb-2" id="socialLinkRequestDto.telegram"
                   name="socialLinkRequestDto.telegram" placeholder="telegram"/>

            <input type="text" class="form-control mb-2" id="socialLinkRequestDto.facebook"
                   name="socialLinkRequestDto.facebook" placeholder="facebook"/>

            <input type="text" class="form-control mb-2" id="socialLinkRequestDto.linkedin"
                   name="socialLinkRequestDto.linkedin" placeholder="linkedin"/>


            <div class="form-check mb-3">
                <input type="checkbox" id="resumeRequestDto.isActive" name="resumeRequestDto.isActive"
                       class="form-check-input" checked>
                <label for="resumeRequestDto.isActive"
                       class="form-check-label"><@spring.message "createResume.actively"/></label>
                <#--                <@spring.showErrors "resumeRequestDto.isActive" "error text-danger" />-->
            </div>


            <button type="submit" class="btn btn-success w-100"><@spring.message "createResume.publish"/></button>
        </form>
    </div>
    <script src="/js/resume-form.js"></script>
</@main.layout>

