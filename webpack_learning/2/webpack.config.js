module.exports = {
    entry:{
        home:'./js/home.js',
        signup: './js/signup.js'
    },
    output:{
        filename:'[name].bundle.js',
        path:__dirname+'/dist',
    }
}
