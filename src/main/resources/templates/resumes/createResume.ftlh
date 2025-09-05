
<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout ; spring>

    <div class="container mt-4 p-4 border rounded" style="max-width:600px;">
        <h3 class="text-danger mb-4 text-center"><@spring.message"createResume.creating_a_resume"/>:</h3>

        <form action="/resumes/create" method="post">

            <#if _csrf??>
                <input
                        type="hidden"
                        name="${(_csrf.parameterName)!'csrf-param-name'}"
                        value="${(_csrf.token)!'csrf-token'}"/>
            </#if>

            <input type="hidden" name="applicantId" value="${applicantId}">

            <div class="mb-3">
                <label for="name" class="form-label"><@spring.message"createResume.resume_title"/>:</label>
                <input type="text" id="name" name="name" class="form-control" placeholder="<@spring.message"createResume.placeholder.resume_title"/>" required>
            </div>

            <div class="mb-3">
                <label><@spring.message"createResume.category"/>:
                    <select name="categoryId" required>
                        <option value=""><@spring.message"createResume.select_category"/></option>
                        <#list categories as category>
                            <option value="${category.id}" <#if resume?has_content && resume.categoryId?? && (resume.categoryId?string == category.id?string)>selected</#if>>

                                ${category.name}
                            </option>
                        </#list>
                    </select>
                </label>
            </div>

            <div class="mb-3">
                <label for="salary" class="form-label"><@spring.message"createResume.expected_salary_level"/>:</label>
                <input type="number" id="salary" name="salary" class="form-control" placeholder="<@spring.message"createResume.enter_salary"/>" required>
            </div>

            <div class="mb-3">
                <label><@spring.message"createResume.experience"/>:</label>
                <div class="mb-2">
<#--                    <input type="text" name="workExperienceInfoList[0].companyName" class="form-control mb-2" placeholder="Компания" required>-->
<#--                    <input type="text" name="workExperienceInfoList[0].position" class="form-control mb-2" placeholder="Должность" required>-->
<#--                    <input type="number" name="workExperienceInfoList[0].years" class="form-control" placeholder="Годы" required>-->
                    <div class="form-outline mb-2">
                        <label class="form-label" for="companyName"><@spring.message"createResume.company"/></label>
                        <@spring.formInput "workExperienceDto.companyName" 'id="companyName" class="form-control"' />
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>

                    <input type="text" name="workExperienceInfoList[0].position" class="form-control mb-2" placeholder="<@spring.message"createResume.job_title"/>" required>
                                        <input type="number" name="workExperienceInfoList[0].years" class="form-control" placeholder="<@spring.message"createResume.years"/>" required>

<#--                        <div data-mdb-input-init class="form-outline mb-4">-->
<#--                            <label class="form-label" for="form3Example3cg">Your Email</label>-->
<#--                            <@spring.formInput "userDto.email" 'id="form3Example3cg" class="form-control form-control-lg"' />-->
<#--                            <@spring.showErrors "<br>" "error text-danger" />-->
<#--                        </div>-->


                    </div>
                </div>



            <div class="mb-3">
                <label><@spring.message"createResume.education"/>:</label>
                <div class="mb-2">
                    <input type="text" name="educationInfoList[0].institution" class="form-control mb-2" placeholder="<@spring.message"createResume.placeholder.educational_institution"/>" required>
                    <input type="text" name="educationInfoList[0].program" class="form-control mb-2" placeholder="<@spring.message"createResume.placeholder.program"/>" required>
                    <input type="date" name="educationInfoList[0].startDate" class="form-control mb-2" required>
                    <input type="date" name="educationInfoList[0].endDate" class="form-control mb-2" required>
                    <input type="text" name="educationInfoList[0].degree" class="form-control" placeholder="<@spring.message"createResume.placeholder.degree"/>" required>
                </div>

            </div>

            <div class="mb-3">
                <label><@spring.message"createResume.social_links"/>:</label>
                <input type="text" name="socialLinkRequestDto.telegram" class="form-control mb-2" placeholder="<@spring.message"createResume.placeholder.telegram_link"/>">
                <input type="text" name="socialLinkRequestDto.facebook" class="form-control mb-2" placeholder="<@spring.message"createResume.placeholder.facebook_link"/>">
                <input type="text" name="socialLinkRequestDto.linkedin" class="form-control" placeholder="<@spring.message"createResume.placeholder.linkedin_link"/>">
            </div>



            <div class="form-check mb-3">
                <input type="checkbox" id="isActive" name="isActive" class="form-check-input" checked>
                <label for="isActive" class="form-check-label"><@spring.message"createResume.actively"/></label>
            </div>

            <button type="submit" class="btn btn-success w-100"><@spring.message"createResume.publish"/></button>
        </form>
    </div>

</@main.layout>
