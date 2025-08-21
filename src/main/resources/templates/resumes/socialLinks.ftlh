
<div class="social-links">
    <h3>Социальные сети</h3>

    <form action="/resume/${resume.id}/social-links" method="post">

        <#if _csrf??>
            <input
                    type="hidden"
                    name="${(_csrf.parameterName)!'csrf-param-name'}"
                    value="${(_csrf.token)!'csrf-token'}"/>
        </#if>


        <input type="hidden" name="resumeId" value="${resume.id}"/>

        <div>
            <label for="telegram">Telegram:</label>
            <input type="text" id="telegram" name="telegram" value="${socialLinks.telegram!}" placeholder="@username"/>
        </div>

        <div>
            <label for="facebook">Facebook:</label>
            <input type="text" id="facebook" name="facebook" value="${socialLinks.facebook!}" placeholder="facebook.com/..."/>
        </div>

        <div>
            <label for="linkedin">LinkedIn:</label>
            <input type="text" id="linkedin" name="linkedin" value="${socialLinks.linkedin!}" placeholder="linkedin.com/in/..."/>
        </div>

        <div>
            <button type="submit">Сохранить</button>
        </div>
    </form>
</div>
