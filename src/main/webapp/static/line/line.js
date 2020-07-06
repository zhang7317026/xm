const padding = 8;/* table padding */
const line = {
    init: function (leftObj, rightObj) {
        this.draw = SVG('draw').size("100%", "100%");
        this.lineArr = [];
        this.currentInfo = {};
        this.createList(leftObj);
        this.createList(rightObj);
        this.bindBtnEvent();
        this.bindParentsEvent();
    },
    /* 创建列表 */
    createList: function (obj, callback) {
        let type = obj.type,
            data = obj.data;
        
        if (type == 'left') {
            $('.left-list').empty();
            data.forEach(element => {
                var item = '<tr class="left-li" data-value="' +element.value+ '">' 
                	+ '<td>'+element.title1+'</td><td class="line-begin">'+element.title2+'</td>'
                	+ '</tr>';
                var obj = {};
                obj.beginValue = element.value;
                obj.line = this.createLine();
                this.lineArr.push(obj)
                $('.left-list').append(item);
            });
            
        } else {
            $('.right-list').empty();
            data.forEach(element => {
                var item = '<tr class="right-li" data-value="' +element.value+ '">' 
                + '<td class="line-end">'+element.title1+'</td><td>'+element.title2+'</td>'
                '</tr>';
                $('.right-list').append(item);
            });

        }
        // this.itemForEach(true);
    },
    /* 绑定按钮事件 */
    bindBtnEvent: function () {
        let self = this,
            parentPosition = $('.left-list').offset();
        /* 鼠标按下left-list列，调整线条开始位置 */
        $('.left-list').find('.line-begin').on('mousedown', function (e) {

            let current = self.lineArr.find(el => {
                return el.beginValue == $(this).parent().attr('data-value');
            });
            current.begin = {};
            current.beginElement = this;
            current.begin.y = $(this).offset().top + $(this).height()/2 + padding ;
            current.begin.x = $(this).offset().left + $(this).width() + padding*2 + 5;
            
            current.line.show();
            current.line.stroke({
                color: "#67C23A",
            });
            current.line.plot(current.begin.x, current.begin.y, current.begin.x, current.begin.y);
            current.end = {};
            /* 如果存在结束位置，删除 */
            if (current.endElement) {
                $(current.endElement).removeClass('selected');
                $(this).removeClass('selected');
            }
            current.endElement = '';
            current.endValue = '';
            self.currentInfo = current;
        });
        /* 鼠标按下right-list列，调整线条结束位置 */
        $('.right-list').find('.line-end').on('mouseup', function (e) {
            let current = self.lineArr.find(el => {
                return el.beginValue == self.currentInfo.beginValue;
            });

            current.end.y = $(this).offset().top + $(this).height()/2 + padding ;
            current.end.x = $(this).offset().left - 8;
            current.endElement = this;
            current.endValue = $(this).parent().attr('data-value');
            
            current.line.plot(current.begin.x, current.begin.y, current.end.x, current.end.y);
            $(current.beginElement).addClass('selected');
            $(current.beginElement).attr('data-selected', current.endValue);
            $(this).addClass('selected');

            self.currentInfo = '';
        });
        /* 自动连线 */
        $('#j-default').click(function (e) {
        	$('#j-reset').click();
            self.itemForEach();
        });
        /* 重置 */
        $('#j-reset').click(function (e) {
            self.lineArr.forEach(el => {
                $(el.beginElement).removeClass("selected");
                $(el.beginElement).attr('data-selected', '');
                $(el.endElement).removeClass("selected");
                el.line.hide();
            });
        });
        /* 结果 */
        $('#j-result').click(function (e) {
        	let result = "";
            $('.left-list .line-begin').each(function (el) {
                let leftSelected = $(this).parent().attr('data-value'),
                	rightSelected = $(this).attr('data-selected');
                result += "左侧("+leftSelected+")--->右侧("+rightSelected+") \n";
            });
            alert(result);
        });
    },
    /* 绑定父亲事件事件 */
    bindParentsEvent: function (params) {
        let self = this;

        $(document).mouseup(function (e) {
            if (!$(e.target).is(".right-li") && self.currentInfo.line) {
                self.currentInfo.line.hide();
                $("#draw")
                    .find(".left-li")
                    .removeClass("display-block-hover");
            }
        })
        $('#draw').mousemove(function (e) {
            e.preventDefault();
            if (Object.keys(self.currentInfo).length != 0) {
                let end = {}
                end.x = self.getMousePos(event).x - $("#draw").offset().left;
                end.y = self.getMousePos(event).y - $("#draw").offset().top;
                self.currentInfo.line.plot(self.currentInfo.begin.x, self.currentInfo.begin.y, end.x, end.y);
            }
        })
    },
    /* 创建线条 */
    createLine: function () {
        let self = this,
            line = self.draw.line();
        line.stroke({
            color: "#67C23A",
            width: 3,
            opacity: 0.8,
            linecap: "round"
        });
        line.hide()
        line.click(function () {
            let current = self.lineArr.find(el => {
                return el.line == this;
            });
            $(current.beginElement).removeClass("selected");
            $(current.endElement).removeClass("selected");
            $(current.beginElement).attr('data-selected', '')

            current.endValue = "";
            current.endElement = "";
            current.end = "";

            this.hide();
        });
        line.mouseover(function () {
            let current = self.lineArr.find(el => {
                return el.line == this;
            });
            if (current.endValue) {
                let left, top;
                left =
                    (current.end.x + current.begin.x - 20) / 2 + "px";
                top =
                    (current.end.y + current.begin.y - 20) / 2 + "px";
                this.addClass("hover-g");
            }
        });
        line.mouseout(function () {
            this.removeClass("hover-g");
        });
        line.marker("end", 4, 4, function (add) {
            add.polyline([
                [1, 0],
                [1, 4],
                [4, 2],
                [1, 0]
            ]);
            this.fill("#67C23A");
            this.stroke({
                color: "#67C23A",
                opacity: 0.8,
                width: 1
            });
        });
        return line;
    },
    /* 遍历left-list，存在默认right，就去right-list找到，进行连接 */
    itemForEach: function (flag) {
    	
        let self = this,
            parentPosition = $('#draw').offset();

        $('td').removeClass('selected');
        $('.left-list .line-begin').each(function (params) {
            let obj = {},
                _this = $(this),
                beginValue = _this.parent().attr('data-value'),
                endValue = _this.parent().attr('data-value');

            obj = self.lineArr.find(el => el.beginValue == beginValue);
            obj.beginElement = this;
            obj.begin = {};
            obj.begin.y = $(this).offset().top + $(this).height()/2 + padding ;
            obj.begin.x = $(this).offset().left + $(this).width() + padding*2 + 5;
            $(this).attr('data-selected', '');
            //判断是否存在初始答案
            if (endValue && !flag) {
                $('.right-list .line-end').each(function (params) {
                    if ($(this).parent().attr('data-value') == endValue) {
                        obj.end = {};

                        obj.end.y = $(this).offset().top + $(this).height()/2 + padding;
                        obj.end.x = $(this).offset().left - 8;
                        obj.endElement = this;
                        obj.endValue = endValue;
                        obj.line.stroke({
                            color: "#E6A23C",
                        });
                        obj.line.plot(obj.begin.x, obj.begin.y, obj.end.x, obj.end.y);
                        obj.line.show();
                        $(this).addClass("selected");
                        _this.addClass("selected");
                        _this.attr("data-selected", endValue);
                    }
                })
            }
        })
    },
    /* 获取鼠标的坐标 */
    getMousePos: function (event) {
        var e = event || window.event;
        var scrollX =
            document.documentElement.scrollLeft || document.body.scrollLeft;
        var scrollY =
            document.documentElement.scrollTop || document.body.scrollTop;
        var x = e.pageX || e.clientX + scrollX;
        var y = e.pageY || e.clientY + scrollY;
        //alert('x: ' + x + '\ny: ' + y);
        return {
            x: x,
            y: y
        };
    },
};
const left = [
		{
			title1: 'id',
			title2: 'varchar',
			value: 'id'
		},
		{
			title1: 'name',
			title2: 'varchar',
			value: 'name'
		},
		{
			title1: 'class',
			title2: 'varchar',
			value: 'class'
		},
		{
			title1: 'grade',
			title2: 'varchar',
			value: 'grade'
		},
		{
			title1: 'sex',
			title2: 'int',
			value: 'sex'
		}
	],
    right = [
 		{
			title1: 'ID',
			title2: 'varchar',
			value: 'id'
		},
		{
			title1: 'age',
			title2: 'varchar',
			value: 'age'
		},
		{
			title1: 'name',
			title2: 'varchar',
			value: 'name'
		},
		{
			title1: 'GRADE',
			title2: 'varchar',
			value: 'grade'
		},
		{
			title1: 'sex',
			title2: 'int',
			value: 'sex'
		}
	];
let leftObj = {
		data: left,
		type: 'left'
	},
	rightObj = {
		data: right,
		type: 'right'
	}
line.init(leftObj, rightObj);