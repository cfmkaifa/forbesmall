<ul class="pagination">
    [#if hasPrevious]
        <li>
            <a href="[@pattern?replace("{pageNumber}", "${previousPageNumber}")?interpret /]"
               data-page-number="${previousPageNumber}">上一页</a>
        </li>
    [#else]
        <li class="disabled">
            <a href="javascript:;">上一页</a>
        </li>
    [/#if]
    [#list segment as segmentPageNumber]
        <li[#if segmentPageNumber == pageNumber] class="active"[/#if]>
            <a href="[@pattern?replace("{pageNumber}", "${segmentPageNumber}")?interpret /]" data-page-number="${segmentPageNumber}">
                [#if totalPages == segmentPageNumber]
                    ${segmentPageNumber}(end)
                [#else]
                    ${segmentPageNumber}
                [/#if]
            </a>
        </li>
    [/#list]
    [#if hasNext]
        <li>
            <a href="[@pattern?replace("{pageNumber}", "${nextPageNumber}")?interpret /]"
               data-page-number="${nextPageNumber}">下一页</a>
        </li>
    [#else]
        <li class="disabled">
            <a href="javascript:;">下一页</a>
        </li>
    [/#if]
    <input id="pageInputNumber" class="page-input" />
    <button class="page-go"><a  id="page-go" href="#" onclick="toPage(this)">GO</a></button>
</ul>