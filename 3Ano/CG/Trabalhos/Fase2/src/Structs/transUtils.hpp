
namespace transUtils {
    enum class transtype { none, translate, rotate, scale, color };

    class transform {
        float x;
        float y;
        float z;
        float angle;
        transtype type;

    public:
        void setTransform();
        void setTransform(float, float, float, float, transtype);
        float getX();
        float getY();
        float getZ();
        float getAngle();
        transtype getType();
    };
}