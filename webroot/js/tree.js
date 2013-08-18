(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/ko_KR/all.js#xfbml=1&appId=146140765593896";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));

function treeCtrl($scope, $http) {
	if ($('#owner').val() == "") $('#owner').val('d2festTeams');
	if ($('#repo').val() == "") $('#repo').val('hirrit');

	$scope.search = function() {

		// 초기화
		$('#content').empty();
		$scope.comments = {};

		// tree 목록을 조회한다.
		$http.get('https://api.github.com/repos/'+$('#owner').val()+'/'+$('#repo').val()+'/git/refs').success(function(data) {
			$.each(data, function(idx, e) {
				if (e.ref == 'refs/heads/master') {
					$http.get('https://api.github.com/repos/'+$('#owner').val()+'/'+$('#repo').val()+'/git/trees/' + e.object.sha).success(function(data) {
						$scope.trees = data.tree;
					});  
				}
			});
		});
	}

	angular.element('document').ready(function() {
		$scope.search();
	});

	$scope.parent = {};

	$scope.go = function(tree) {
		if (tree == 'parent') {

			// 상위 tree 로 이동
			$scope.trees = $scope.parent;

		} else if (tree.type == "blob") {

			// blob 타입일 경우 내용을 보여준다.
			$http({
				url: 'https://api.github.com/repos/'+$('#owner').val()+'/'+$('#repo').val()+'/git/blobs/' + tree.sha, 
				method: 'GET', 
					headers: {'Accept': 'application/vnd.github.VERSION.raw' }
				}).success(function(code) {

					$scope.comments = {};
					$http({
						url: '/json/comment_sample.json',
						method: 'GET'
					}).success(function(comments) {
						$.each(comments, function(idx, comment) {
							if (tree.sha == comment.source_id) {
								$scope.comments[comment.end_line] = comment;
							}

						});

						$('#content').empty();

						var source = '';
						var lines = code.split("\n");
						$scope.lines = lines;

						d = document.createElement('pre');

						// 라인 넘버 추가
						for (var i = 0, j = lines.length; i < j; i++) {
							var line = lines[i];
							source += line + "\n";
						}

						// review 와 code 를 구분
						$(d).addClass('prettyprint linenums');
						$(d).text(source);
						$(d).appendTo($('#content'));

						// css 를 다시 적용
						$('.prettyprint').removeClass("prettyprinted");
						prettyPrint();
					});
			});

		} else {

			// 상위 디렉토리로 이동하기 위해 현재 tree 를 parent 에 저장
			if ($scope.trees != undefined) $scope.parent = $scope.trees;

			// 새로운 tree 탐색
			$http.get('https://api.github.com/repos/'+$('#owner').val()+'/'+$('#repo').val()+'/git/trees/' + tree.sha).success(function(data) {
				$('#parent').removeClass('hide');
				$scope.trees = data.tree;
				$('#content').empty();
			});

		}
	}

	$scope.viewComment = function(end_line) {
		$('#content').empty();

		// temp
		var comment = $scope.comments[end_line];

		var source = '';
		var lines = $scope.lines;

		d = document.createElement('pre');

		// 라인 넘버 추가
		var line = '';
		for (var i = 0, j = lines.length; i < j; i++) {
			line = lines[i];
			
			// review 의견 추가
			if (comment.start_line == i + 1) {

				// 이전 내용을 저장
				$(d).addClass('prettyprint');
				$(d).text(source);
				$(d).appendTo($('#content'));

				source = '';

				// review 블록 지정
				for (j = comment.end_line; i < j; i++) {
					line = lines[i];
					source += (i+1) + "\t" + line + "\n";
				}

				d = document.createElement('pre');
				$(d).addClass('prettyprint');
				$(d).text(source);
				$(d).appendTo($('#content'));

				// review 내용 보기
				d = document.createElement('div');

				/*
				var review = '<div class="panel" style="width: 90%; margin-left: 40px">';
				review += comment.comment;
				review += '</div>';	// end of panel
				*/

				var review = '<div class="fb-comments" data-href="http://daclouds.blog.me" data-width="470" style="width: 470px; margin-left: 80px;"></div>';

				$(d).html(review);
				$(d).appendTo($('#content'));

				d = document.createElement('pre');
				source = '';

				j = lines.length;
			}

			source += (i+1) + "\t" + line + "\n";
		}

		// review 와 code 를 구분
		$(d).addClass('prettyprint');
		$(d).text(source);
		$(d).appendTo($('#content'));

		// css 를 다시 적용
		$('.prettyprint').removeClass("prettyprinted");
		//$('.prettyprint').css({ margin: "20px" });
		prettyPrint();

		FB.XFBML.parse();
	}
}