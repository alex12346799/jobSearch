
<#--<#import "../layout.ftlh" as main>-->
<#--<#import "/spring.ftl" as spring>-->
<#--<@main.layout; spring>-->

<#--    <div class="container mt-4 p-4 border rounded" style="max-width:600px;">-->
<#--        <h3 class="text-danger mb-4 text-center"><@spring.message "createResume.creating_a_resume"/>:</h3>-->

<#--        <form action="/resumes/create" method="post">-->

<#--            <#if _csrf??>-->
<#--                <input-->
<#--                        type="hidden"-->
<#--                        name="${(_csrf.parameterName)!'csrf-param-name'}"-->
<#--                        value="${(_csrf.token)!'csrf-token'}"/>-->
<#--            </#if>-->

<#--            <input type="hidden" name="applicantId" value="${applicantId}">-->

<#--            <!-- Название резюме &ndash;&gt;-->
<#--            <div data-mdb-input-init class="form-outline mb-4">-->
<#--                <label class="form-label" for="form3Example4cg"><@spring.message "createResume.resume_title"/>:</label>-->
<#--                <@spring.formInput "resumeRequestDto.name" 'id="form3Example4cg" class="form-control form-control-lg"' />-->
<#--                <@spring.showErrors "resumeRequestDto.name" "error text-danger" />-->
<#--            </div>-->

<#--            <!-- Категория &ndash;&gt;-->
<#--            <div class="mb-3">-->
<#--                <label>Категория</label>-->
<#--                <select name="categoryId" class="form-select" required>-->
<#--                    <option value="">выберите категорию</option>-->
<#--                    <#list categories as category>-->
<#--                        <option value="${category.id}"-->
<#--                                <#if resumeRequestDto.categoryId?? && resumeRequestDto.categoryId == category.id>selected</#if>>-->
<#--                            ${category.name}-->
<#--                        </option>-->
<#--                    </#list>-->
<#--                </select>-->


<#--            </div>-->


<#--            <div class="mb-3">-->
<#--                <label class="form-label" for="form3Example4cg1"><@spring.message "createResume.expected_salary_level"/>:</label>-->
<#--                <@spring.formInput "resumeRequestDto.salary" 'id="form3Example4cg1" class="form-control form-control-lg"' />-->
<#--                <@spring.showErrors "resumeRequestDto.salary"  "error text-danger" />-->
<#--            </div>-->


<#--            <div class="mb-3">-->
<#--                <label><@spring.message "createResume.experience"/>:</label>-->
<#--                <div class="mb-2">-->
<#--                    <div class="form-outline mb-2">-->
<#--                        <label class="form-label" for="companyName"><@spring.message "createResume.company"/></label>-->
<#--                        <@spring.formInput "resumeRequestDto.workExperienceInfoList[0].companyName"-->
<#--                        'id="companyName" class="form-control"' />-->
<#--                        <@spring.showErrors "resumeRequestDto.workExperienceInfoList[0].companyName"  "error text-danger" />-->
<#--                    </div>-->

<#--                    <div class="form-outline mb-2">-->
<#--                        <label class="form-label" for="position"><@spring.message "createResume.job_title"/></label>-->
<#--                        <@spring.formInput "resumeRequestDto.workExperienceInfoList[0].position"-->
<#--                        'id="position" class="form-control"' />-->
<#--                        <@spring.showErrors "resumeRequestDto.workExperienceInfoList[0].position"  "error text-danger" />-->
<#--                    </div>-->

<#--                    <div class="form-outline mb-2">-->
<#--                        <label class="form-label" for="years"><@spring.message "createResume.years"/></label>-->
<#--                        <@spring.formInput "resumeRequestDto.workExperienceInfoList[0].years"-->
<#--                        'id="years" type="number" class="form-control"' />-->
<#--                        <@spring.showErrors "resumeRequestDto.workExperienceInfoList[0].years" "error text-danger" />-->
<#--                    </div>-->
<#--                </div>-->
<#--            </div>-->


<#--            <div class="mb-3">-->
<#--                <label><@spring.message "createResume.education"/>:</label>-->
<#--                <div class="mb-2">-->
<#--                    <div class="form-outline mb-2">-->
<#--                        <label class="form-label" for="institution"><@spring.message "createResume.placeholder.educational_institution"/></label>-->
<#--                        <@spring.formInput "resumeRequestDto.educationInfoList[0].institution"-->
<#--                        'id="institution" class="form-control"' />-->
<#--                        <@spring.showErrors "resumeRequestDto.educationInfoList[0].institution"  "error text-danger" />-->
<#--                    </div>-->

<#--                    <div class="form-outline mb-2">-->
<#--                        <label class="form-label" for="program"><@spring.message "createResume.placeholder.program"/></label>-->
<#--                        <@spring.formInput "resumeRequestDto.educationInfoList[0].program"-->
<#--                        'id="program" class="form-control"' />-->
<#--                        <@spring.showErrors "resumeRequestDto.educationInfoList[0].program"  "error text-danger" />-->
<#--                    </div>-->

<#--                    <div class="form-outline mb-2">-->
<#--                        <label class="form-label" for="degree"><@spring.message "createResume.placeholder.degree"/></label>-->
<#--                        <@spring.formInput "resumeRequestDto.educationInfoList[0].degree"-->
<#--                        'id="degree" class="form-control"' />-->
<#--                        <@spring.showErrors "resumeRequestDto.educationInfoList[0].degree"  "error text-danger" />-->
<#--                    </div>-->

<#--                    <div class="form-outline mb-2">-->
<#--                        <label class="form-label" for="startDate"><@spring.message "createResume.start_date"/></label>-->
<#--                        <@spring.formInput "resumeRequestDto.educationInfoList[0].startDate"-->
<#--                        'id="startDate" type="date" class="form-control"' />-->
<#--                        <@spring.showErrors "resumeRequestDto.educationInfoList[0].startDate" "error text-danger" />-->
<#--                    </div>-->

<#--                    <div class="form-outline mb-2">-->
<#--                        <label class="form-label" for="endDate"><@spring.message "createResume.end_date"/></label>-->
<#--                        <@spring.formInput "resumeRequestDto.educationInfoList[0].endDate"-->
<#--                        'id="endDate" type="date" class="form-control"' />-->
<#--                        <@spring.showErrors "resumeRequestDto.educationInfoList[0].endDate"  "error text-danger" />-->
<#--                    </div>-->
<#--                </div>-->
<#--            </div>-->


<#--            <div class="mb-3">-->
<#--                <label><@spring.message "createResume.social_links"/>:</label>-->


<#--                <@spring.formInput "resumeRequestDto.socialLinkRequestDto.telegram" 'class="form-control mb-2" placeholder=telegram' />-->
<#--                <@spring.showErrors "resumeRequestDto.socialLinkRequestDto.telegram"  "error text-danger" />-->

<#--                <@spring.formInput "resumeRequestDto.socialLinkRequestDto.facebook" 'class="form-control mb-2" placeholder=facebook' />-->
<#--                <@spring.showErrors "resumeRequestDto.socialLinkRequestDto.facebook" "error text-danger" />-->

<#--                <@spring.formInput "resumeRequestDto.socialLinkRequestDto.linkedin" 'class="form-control" placeholder=linkedin' />-->
<#--                <@spring.showErrors "resumeRequestDto.socialLinkRequestDto.linkedin"  "error text-danger" />-->
<#--            </div>-->




<#--            <div class="form-check mb-3">-->
<#--                <input type="hidden" name="resumeRequestDto.isActive" value="false"/>-->
<#--                <input type="checkbox" id="resumeRequestDto.isActive" name="resumeRequestDto.isActive" class="form-check-input" checked>-->
<#--                <label for="resumeRequestDto.isActive" class="form-check-label"><@spring.message "createResume.actively"/></label>-->
<#--                <@spring.showErrors "resumeRequestDto.isActive" "error text-danger" />-->
<#--            </div>-->


<#--            <button type="submit" class="btn btn-success w-100"><@spring.message "createResume.publish"/></button>-->
<#--        </form>-->
<#--    </div>-->

<#--</@main.layout>-->







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
                        <input type="number" name="WorkExperienceInfoList[0].years" placeholder="Годы"
                               class="form-control mb-2"/>
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


            <#--            <div class="mb-3">-->
            <#--                <label>Образование:</label>-->
            <#--                <div id="educationContainer">-->
            <#--                    <div class="mb-2">-->
            <#--                        <input type="text" name="resumeRequestDto.educationInfoList[0].institution" placeholder="Учебное заведение" class="form-control mb-2" />-->
            <#--                        <input type="text" name="resumeRequestDto.educationInfoList[0].program" placeholder="Программа" class="form-control mb-2" />-->
            <#--                        <input type="text" name="resumeRequestDto.educationInfoList[0].degree" placeholder="Степень" class="form-control mb-2" />-->
            <#--                        <input type="date" name="resumeRequestDto.educationInfoList[0].startDate" class="form-control mb-2" />-->
            <#--                        <input type="date" name="resumeRequestDto.educationInfoList[0].endDate" class="form-control mb-2" />-->
            <#--                    </div>-->
            <#--                </div>-->
            <#--                <button type="button" class="btn btn-primary btn-sm" onclick="addEducation()">+ Добавить образование</button>-->
            <#--            </div>-->


            <div class="mb-3">
                <label><@spring.message "createResume.social_links"/>:</label>


                <#--                <@spring.formInput "resumeRequestDto.socialLinkRequestDto.telegram" 'class="form-control mb-2" placeholder=telegram' />-->
                <#--                <@spring.showErrors "resumeRequestDto.socialLinkRequestDto.telegram"  "error text-danger" />-->
                <input type="text" class="form-control mb-2" id="socialLinkRequestDto.telegram" name="telegram"
                       placeholder="telegram"/>

                <#--                <@spring.formInput "resumeRequestDto.socialLinkRequestDto.facebook" 'class="form-control mb-2" placeholder=facebook' />-->
                <input type="text" class="form-control mb-2" id="socialLinkRequestDto.facebook" name="facebook"
                       placeholder="facebook"/>
                <#--                <@spring.showErrors "resumeRequestDto.socialLinkRequestDto.facebook" "error text-danger" />-->

                <input type="text" class="form-control mb-2" id="socialLinkRequestDto.linkedin" name="facebook"
                       placeholder="linkedin"/>
                <#--                <@spring.formInput "resumeRequestDto.socialLinkRequestDto.linkedin" 'class="form-control" placeholder=linkedin' />-->
                <#--                <@spring.showErrors "resumeRequestDto.socialLinkRequestDto.linkedin"  "error text-danger" />-->
            </div>


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

