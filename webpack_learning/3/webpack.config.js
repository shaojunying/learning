module.exports = {
    // 入口文件
    entry: './js/index',
    output:{
        filename:'bundle.js',
        path: __dirname+"/dist"
    },
    module:{
        rules:[
            {
                test: /\.css$/,
                use: [
                  { loader: "css-loader" }
                ]
            }
        ]
    }
}
