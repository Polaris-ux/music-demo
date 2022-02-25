// 提示信息工具类
export const mixin={
    methods:{
        notify(title,type){
            this.$notify({
                title: title,
                type: type
            })
        },
        // 根据相对地址获取绝对地址
        getUrl(url){
            return `${this.$store.state.HOST}${url}`;
        },
        // 性别转换为中文
        changeSex(val){
            if(val==0){
                return '女';
            }else if(val==1){
                return '男';
            }else if(val==2){
                return '组合';
            }else{
                return '不明';
            }
        },
        // 获取出生日期
        awareBirth(val){
            return String(val).substring(0,10);
        },
        // 上传图片前对图片进行校验
        beforeAvatorUpload(file){
            let isJPG=(file.type==='image/jpeg')||(file.type==='image/png');
            if(!isJPG){
                this.$message.error("上传图片格式必须为jpg或png");
                return false;
            }
            let sizeLt2M=file.size/(1024*1024)<2;
            if(!sizeLt2M){
                this.$message.error("上传图片大小不能大于2M");
                return false;
            }
            return true;
        },
        // 上传图片成功之后要做的工作
        handleAvatorSuccess(res){
            console.log(res);
            let _this = this;
            if(res.code==='50201'){
                _this.notify(res.message,'success');
                console.log(_this.$refs);
                _this.$nextTick(()=>{
                    _this.$refs.uploadSingerAvator.clearFiles();
                })
                _this.getSingers(null);
            }else if(res.code=='50200'){
                _this.notify("上传图片成功",'success');
                _this.$refs.uploadAvator.clearFiles();
                _this.getSongs(null);
            }else{
                _this.notify(res.message,'error');
            }

        },
        // 上传歌曲前的准备工作
        handleMusicUpload(file){
            let isAudio=file.type=='audio/mpeg'||file.type=='audio/ogg'||file.type=='audio/wav';
            if(!isAudio){
                this.notify("非支持的音频格式",'error');
                return false;
            }
            let sizeLt20M=file.size<(1024*1024*20);
            if(!sizeLt20M){
                this.notify("文件最大支持20M",'error');
                return false;
            }
            return true;
        },
        // 超出上传文件个数时
        handleExceed(){
            this.notify("只支持上传一个附件",'error');
        },

        // 自定义上传文件方法，会覆盖el-upload默认的方法
        httpRequest(file){
            console.log(file);
            this.fileList.push(file);
        },
        // 上传音乐成功后
        handleMusicUploadSuccess(res){
            let _this = this;
            
            if(res.code=="50200"){
                _this.notify("修改音频成功",'success');
                _this.$refs.uploadSongSource.clearFiles();
                _this.getSongs(null);
                
            }else{
                _this.notify(res.message,'error');
            }
        },
        // 删除歌手的准备工作
        handleRemove(id){
            this.idx=id;
            this.delVisible=true;
        },
        // 执行删除歌曲的准备工作
        handleRemoveSong(song){
            this.delForm.id=song.id;
            this.delForm.pic=song.pic;
            this.delForm.url=song.url;
            this.delVisible=true;
        },
        // 执行删除歌单的准备工作
        handleRemoveSongList(id,pic){
            this.delForm={
                id:id,
                pic:pic
            }
            this.delVisible=true;
        },
        // 多项选择删除
        handleSelectChange(val){
            this.mulDelIds=[];
            this.paths=[];
            for(let item of val){
                this.mulDelIds.push(item.id);
                this.paths.push(item.pic);
                if(item.singerId!=null){
                    this.paths.push(item.url);
                }
            }
        },
        showPass(){
            // 点击图标是密码隐藏或显示
            if(this.passw=='text'){
              this.passw='password';
              // 更换图标
              this.icon="el-input__icon el-icon-view";
            }else{
              this.passw='text';
              this.icon="el-input__icon el-icon-edit";
            }
        }
    }
}