function request(url, data,method) {
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: url,
            data: data,
            method: method,
            header: {
                'Content-Type': 'application/json',
                'token':wx.getStorageSync('token')
            },
            dataType:"json",
            success: function (res) {
                if (res.statusCode == 200) {
                    /**/
                    if (res.data.errcode == 401) {
                        console.log("重新获取token 然后在进行")
                        //需要登录后才可以操作
                        return auth().then(function(result){
                            wx.setStorageSync('token', result.token);
                        request(url,data,method).then(function(res){
                            resolve(res);
                        });
                    }).catch(function(err){
                            console.log(4);
                            reject(err);
                        });
                    } else {
                        resolve(res.data);
                    }
                } else {
                    reject(res);
                }
            },
            fail: function (err) {
                wx.hideLoading();
                reject(err)
                console.log("failed")
            }
        })
    });
};