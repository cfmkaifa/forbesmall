<div class="charge">
			<div class="industryCharge">
				<div class="industry">
					<div class="industryTop">
						<h3 class="title">${articleCategory.name}${message("shop.article.noPermTitle")}</h3>
						<span class="fees">/INDUSTRY REPORT FEES</span>
					</div>
					<div class="industryContent">
						<ul class="content">
							<li class="span3">
								<h4>${articleCategory.name}${message("shop.article.weekly")}</h4>
								<span class="price"><p>${articleCategory.weekSubFee}${message("shop.article.unit")}</p></span>
								<p>${articleCategory.seoKeywords}</p>
								<a class="immediately" subType="weekSubFee" >${message("shop.article.subscribeNow")}</a>
							</li>
							<li class="span3">
								<h4>${articleCategory.name}${message("shop.article.monthly")}</h4>
								<span class="price"><p>${articleCategory.monthSubFee}${message("shop.article.unit")}</p></span>
								<p>${articleCategory.seoKeywords}</p>
								<a class="immediately"  subType="monthSubFee" >${message("shop.article.subscribeNow")}</a>
							</li>
							<li class="span3">
								<h4>${articleCategory.name}${message("shop.article.quarter")}</h4>
								<span class="price"><p>${articleCategory.quarterSubFee}${message("shop.article.unit")}</p></span>
								<p>${articleCategory.seoKeywords}</p>
								<a class="immediately"  subType="quarterSubFee" >${message("shop.article.subscribeNow")}</a>
							</li>
							<li class="span3">
								<h4>${articleCategory.name}${message("shop.article.annual")}</h4>
								<span class="price"><p>${articleCategory.yearSubFee}${message("shop.article.unit")}</p></span>
								<p>${articleCategory.seoKeywords}</p>
								<a class="immediately"  subType="yearSubFee"  >${message("shop.article.subscribeNow")}</a>
							</li>
							
						</ul>
					</div>
				</div>
			</div>
			<div class="membership">
				<div class="industryTop">
					<h3 class="title">${message("shop.article.memberTitle")}</h3>
					<span class="fees">/FOR SUPPLIER</span>
				</div>
				<div class="membershipContent">
					<table class="table">
						<thead>
							<tr>
								<th class="th1">
									<div class="out">
										<b>${message("shop.article.membershipGrade")}</b>
										<em>${articleCategory.name}</em>
									</div>
								</th>
								[#if memberRanks??]
									[#list memberRanks as memberRank]
										<th>${memberRank.name}</th>
									[/#list]
								[/#if]
							</tr>
						</thead>
						<tbody>
							<tr>
								[#if memberRanks??]
									<td>${message("shop.article.weekly")}</td>
									[#list memberRanks as memberRank]
										<td>${articleCategory.weekSubFee}</td>
									[/#list]
								[/#if]
							</tr>
							<tr>
								[#if memberRanks??]
									<td>${message("shop.article.monthly")}</td>
									[#list memberRanks as memberRank]
										<td>${articleCategory.monthSubFee}</td>
									[/#list]
								[/#if]
							</tr>
							<tr>
								[#if memberRanks??]
									<td>${message("shop.article.quarter")}</td>
									[#list memberRanks as memberRank]
										<td>${articleCategory.quarterSubFee}</td>
									[/#list]
								[/#if]
							</tr>
							<tr>
								[#if memberRanks??]
									<td>${message("shop.article.annual")}</td>
									[#list memberRanks as memberRank]
										<td>${articleCategory.yearSubFee}</td>
									[/#list]
								[/#if]
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<!-- 选择身份模态框 -->
			<div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="myModals">
					<div class="modal-header">
						<h3 id="myModalLabel">${message("shop.article.identity")}</h3>
					 </div>
					 <div class="modalBody">
						<form>
							<a id="supplierPay" class="btna btn-defaulta" href="">${message("shop.article.supplier")}</a>
							<a id="purchaserPay" class="btna btn-defaulta" href="">${message("shop.article.purchaser")}</a>
						</form>
					 </div>
				</div>
			</div>
		</div>
<script>
	$(function(){
		$(".immediately").click(function(){
			$('#myModal').modal()
			var subType = $(this).attr("subType");
			var supplierUrl = "${base}/article/subscribe-supplier/${articleCategory.id}";
			var purchaserUrl = "${base}/article/subscribe-purchaser/${articleCategory.id}";
			supplierUrl += "/" + subType;
			purchaserUrl += "/" + subType;
			$("#supplierPay").attr("href",supplierUrl);
			$("#purchaserPay").attr("href",purchaserUrl);
		})
	})
</script>