    //funzioni
    Date.prototype.yyyymmdd = function() {
        var yyyy = this.getFullYear().toString();
        var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based
        var dd  = this.getDate().toString();
        return (dd[1]?dd:"0"+dd[0]) + "/" + (mm[1]?mm:"0"+mm[0]) + "/" + yyyy; // padding
    };

    Date.prototype.addDays = function(days){
        var dat = new Date(this.valueOf());
        dat.setDate(dat.getDate() + days);
        return dat;
    }

    function interpolaKnn(Vx,Vy,x){
        var I=Vx.length;
        var y=0;
        var s=0;
        for(var i=0 ; i<I;i++){
            if(Vx[i]==x)return Vy[i];
            var v=1/Math.abs(Vx[i]-x);
            s=s+v;
            y=y+Vy[i]*v;
        }
        return y/s;
    }