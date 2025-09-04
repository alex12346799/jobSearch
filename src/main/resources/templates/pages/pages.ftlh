<#macro pager url page>
    <#import "/spring.ftl" as spring>

        <#assign body = 1..page.getTotalPages()>

    <div class="mt-3">
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link disabled" href="#"><@spring.message"pages.pages_str"/></a>
            </li>
            <#list body as p>
                <#if (p-1)==page.getNumber()>
                    <li class="page-item active">
                        <a class="page-link" href="#">${p}</a>
                    </li>
                <#else >
                    <li class="page-item">
                        <a class="page-link" href="${url}?page=${p-1}&size=${page.getSize()}">${p}</a>
                    </li>
                </#if>
            </#list>

        </ul>

        <ul class="pagination">
            <li class="page-item">
                <a class="page-link disabled" href="#"><@spring.message"pages.elements_per_page"/></a>
            </li>
            <#list [2,5,10,25,50] as c>
                <#if c==page.getSize()>
                    <li class="page-item active">
                        <a class="page-link" href="#">${c}</a>
                    </li>
                <#else >
                    <li class="page-item">
                        <a class="page-link" href="${url}?page=${page.getNumber()}&size=${c}">${c}</a>
                    </li>
                </#if>
            </#list>

        </ul>
    </div>
</#macro>