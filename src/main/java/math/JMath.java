package math;

import org.joml.Vector2f;

public class JMath {
    /// The number e
    public static final double E = 2.7182818284590452353602874713526624977572470937000d;
    /// The number log\[2]\(e)
    public static final double Log2E = 1.4426950408889634073599246810018921374266459541530d;
    /// The number log\[10]\(e)
    public static final double Log10E = 0.43429448190325182765112891891660508229439700580366d;
    /// The number log\[e]\(2)
    public static final double Ln2 = 0.69314718055994530941723212145817656807550013436026d;
    /// The number log\[e]\(10)
    public static final double Ln10 = 2.3025850929940456840179914546843642076011014886288d;
    /// The number log\[e]\(pi)
    public static final double LnPi = 1.1447298858494001741434273513530587116472948129153d;
    /// The number log\[e]\(2*pi)/2
    public static final double Ln2PiOver2 = 0.91893853320467274178032973640561763986139747363780d;
    /// The number 1/e
    public static final double InvE = 0.36787944117144232159552377016146086744581113103176d;
    /// The number sqrt\(e)
    public static final double SqrtE = 1.6487212707001281468486507878141635716537761007101d;
    /// The number sqrt\(2)
    public static final double Sqrt2 = 1.4142135623730950488016887242096980785696718753769d;
    /// The number sqrt\(3)
    public static final double Sqrt3 = 1.7320508075688772935274463415058723669428052538104d;
    /// The number sqrt\(1/2) = 1/sqrt\(2) = sqrt\(2)/2
    public static final double Sqrt1Over2 = 0.70710678118654752440084436210484903928483593768845d;
    /// The number sqrt\(3)/2
    public static final double HalfSqrt3 = 0.86602540378443864676372317075293618347140262690520d;
    /// The number pi
    public static final double Pi = 3.1415926535897932384626433832795028841971693993751d;
    ///  The number pi*2
    public static final double Pi2 = 6.2831853071795864769252867665590057683943387987502d;
    /// The number pi/2
    public static final double PiOver2 = 1.5707963267948966192313216916397514420985846996876d;
    /// The number pi*3/2
    public static final double Pi3Over2 = 4.71238898038468985769396507491925432629575409906266d;
    /// The number pi/4
    public static final double PiOver4 = 0.78539816339744830961566084581987572104929234984378d;
    /// The number sqrt\(pi)
    public static final double SqrtPi = 1.7724538509055160272981674833411451827975494561224d;
    /// The number sqrt\(2pi)
    public static final double Sqrt2Pi = 2.5066282746310005024157652848110452530069867406099d;
    /// The number sqrt\(pi/2)
    public static final double SqrtPiOver2 = 1.2533141373155002512078826424055226265034933703050d;
    /// The number sqrt\(2*pi*e)
    public static final double Sqrt2PiE = 4.1327313541224929384693918842998526494455219169913d;
    /// The number log\(sqrt\(2*pi))
    public static final double LogSqrt2Pi = 0.91893853320467274178032973640561763986139747363778;
    /// The number log\(sqrt\(2*pi*e))
    public static final double LogSqrt2PiE = 1.4189385332046727417803297364056176398613974736378d;
    /// The number log\(2 * sqrt\(e / pi))
    public static final double LogTwoSqrtEOverPi = 0.6207822376352452223455184457816472122518527279025978;
    /// The number 1/pi
    public static final double InvPi = 0.31830988618379067153776752674502872406891929148091d;
    /// The number 2/pi
    public static final double TwoInvPi = 0.63661977236758134307553505349005744813783858296182d;
    /// The number 1/sqrt\(pi)
    public static final double InvSqrtPi = 0.56418958354775628694807945156077258584405062932899d;
    /// The number 1/sqrt\(2pi)
    public static final double InvSqrt2Pi = 0.39894228040143267793994605993438186847585863116492d;
    /// The number 2/sqrt\(pi)
    public static final double TwoInvSqrtPi = 1.1283791670955125738961589031215451716881012586580d;
    /// The number 2 * sqrt\(e / pi)
    public static final double TwoSqrtEOverPi = 1.8603827342052657173362492472666631120594218414085755;
    /// The number \(pi)/180 - factor to convert from Degree \(deg) to Radians \(rad).
    public static final double Degree = 0.017453292519943295769236907684886127134428718885417d;
    /// The number \(pi)/200 - factor to convert from NewGrad \(grad) to Radians \(rad).
    public static final double Grad = 0.015707963267948966192313216916397514420985846996876d;

    public static void rotate(Vector2f vec, float angleDeg, Vector2f origin) {
        float x = vec.x - origin.x;
        float y = vec.y - origin.y;

        float cos = (float)Math.cos(Math.toRadians(angleDeg));
        float sin = (float)Math.sin(Math.toRadians(angleDeg));

        float xPrime = (x * cos) - (y * sin);
        float yPrime = (x * sin) + (y * cos);

        xPrime += origin.x;
        yPrime += origin.y;

        vec.x = xPrime;
        vec.y = yPrime;
    }

    public static boolean compare(float x, float y, float epsilon) {
        return Math.abs(x - y) <= epsilon * Math.max(1.0f, Math.max(Math.abs(x), Math.abs(y)));
    }

    public static boolean compare(Vector2f vec1, Vector2f vec2, float epsilon) {
        return compare(vec1.x, vec2.x, epsilon) && compare(vec1.y, vec2.y, epsilon);
    }

    public static boolean compare(float x, float y) {
        return Math.abs(x - y) <= Float.MIN_VALUE * Math.max(1.0f, Math.max(Math.abs(x), Math.abs(y)));
    }

    public static boolean compare(Vector2f vec1, Vector2f vec2) {
        return compare(vec1.x, vec2.x) && compare(vec1.y, vec2.y);
    }
}
