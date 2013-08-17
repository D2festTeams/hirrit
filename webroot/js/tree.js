function treeCtrl($scope, $http) {
	if ($('#owner').val() == "") $('#owner').val('d2festTeams');
	if ($('#repo').val() == "") $('#repo').val('hirrit');

	$scope.search = function() {

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
				}).success(function(data) {
				
					$('#content').empty();

					var source = '';
					var lines = data.split("\n");

					d = document.createElement('pre');

					// 라인 넘버 추가
					for (var i = 0, j = lines.length; i < j; i++) {
						var line = lines[i];
						source += (i+1) + "\t" + line + "\n";

						// review 의견 추가
						if ((i+1) % 6 == 0) {
							$(d).addClass('prettyprint');
							$(d).text(source);
							$(d).appendTo($('#content'));

							d = document.createElement('div');

							var review = '<div class="panel" style="width: 90%; margin-left: 40px">';
							review += '별로~~';
							review += '</div>';	// end of panel
							$(d).html(review);
							$(d).appendTo($('#content'));

							d = document.createElement('pre');
							source = '';
						}

						// review 와 code 를 구분
						$(d).addClass('prettyprint');
						$(d).text(source);
						$(d).appendTo($('#content'));
					}

					// css 를 다시 적용
					$('.prettyprint').removeClass("prettyprinted");
					prettyPrint();
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
}