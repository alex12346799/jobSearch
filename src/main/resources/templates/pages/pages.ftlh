<#macro pager url page extraParams="">
    <#import "/spring.ftl" as spring>


    <#assign totalPages = (page.getTotalPages()?number)!0>
    <#assign cur        = ((page.getNumber()?number)!0) + 1>
    <#assign size       = (page.getSize()?number)!10>
    <#assign sep        = (url?contains("?"))?then("&", "?")>

    <#assign effTotal = (totalPages gt 0)?then(totalPages, 1)>
    <#if cur lt 1>
        <#assign cur = 1>
    </#if>
    <#if cur gt effTotal>
        <#assign cur = effTotal>
    </#if>

    <#assign start = cur - 2>
    <#if start lt 1>
        <#assign start = 1>
    </#if>

    <#assign end = cur + 2>
    <#if end gt effTotal>
        <#assign end = effTotal>
    </#if>

    <style>
        .modern-pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 8px;
            padding: 10px 20px;
            background: #fff;
            border-radius: 50px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
            margin-top: 25px;
            flex-wrap: wrap;
        }

        .modern-pagination a, .modern-pagination span {
            display: flex;
            justify-content: center;
            align-items: center;
            min-width: 34px;
            height: 34px;
            padding: 0 10px;
            border-radius: 17px;
            font-size: 14px;
            color: #333;
            text-decoration: none;
            transition: all 0.2s ease;
        }

        .modern-pagination a:hover {
            background: #f0f0ff;
            color: #6c63ff;
        }

        .modern-pagination .active {
            background: #6c63ff;
            color: #fff;
            font-weight: 600;
            border: 1px solid #6c63ff;
        }

        .modern-pagination .ellipsis {
            pointer-events: none;
            color: #999;
        }

        .page-size-selector {
            margin-left: 10px;
            padding: 6px 10px;
            border-radius: 20px;
            border: 1px solid #ddd;
            background: #fff;
            font-size: 13px;
            color: #333;
            cursor: pointer;
            transition: 0.2s;
        }

        .page-size-selector:hover {
            border-color: #6c63ff;
        }
    </style>

    <div class="modern-pagination">


        <#if start gt 1>
            <a href="${url}${sep}page=0&size=${size}<#if extraParams?has_content>&${extraParams}</#if>">1</a>
            <span class="ellipsis">…</span>
        </#if>

        <#list start..end as p>
            <#if p == cur>
                <span class="active">${p}</span>
            <#else>
                <a href="${url}${sep}page=${p - 1}&size=${size}<#if extraParams?has_content>&${extraParams}</#if>">${p}</a>
            </#if>
        </#list>

        <#if end lt effTotal>
            <span class="ellipsis">…</span>
            <a href="${url}${sep}page=${effTotal - 1}&size=${size}<#if extraParams?has_content>&${extraParams}</#if>">${effTotal}</a>
        </#if>

        <select class="page-size-selector"
                onchange="location.href='${url}${sep}page=0&size=' + this.value + '<#if extraParams?has_content>&${extraParams}</#if>'">
            <#list [2,5,10,25,50] as c>
                <option value="${c}" <#if c == size>selected</#if>>${c} / page</option>
            </#list>
        </select>
    </div>
</#macro>
