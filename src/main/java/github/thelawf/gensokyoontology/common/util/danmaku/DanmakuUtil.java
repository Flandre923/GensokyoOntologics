package github.thelawf.gensokyoontology.common.util.danmaku;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.entity.projectile.AbstractDanmakuEntity;
import github.thelawf.gensokyoontology.common.util.logos.math.GSKOMathUtil;
import github.thelawf.gensokyoontology.core.SpellCardRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DanmakuUtil {

    public static final IntIdentityHashBiMap<IDataSerializer<SpellData>> REGISTRY = new IntIdentityHashBiMap<>(16);

    public static final IDataSerializer<SpellData> SPELL_DATA = new IDataSerializer<SpellData>() {
        @Override
        public void write(PacketBuffer buf, @NotNull SpellData value) {
            buf.writeString(SpellCardRegistry.IDO_NO_KAIHO_DATA.getId().toString());
        }

        @Override
        public SpellData read(PacketBuffer buf) {
            return SpellCardRegistry.SPELL_CARD_REGISTRY.getValue(new ResourceLocation(
                    GensokyoOntology.MODID, buf.readString()));
        }

        @Override
        @NotNull
        public SpellData copyValue(@NotNull SpellData value) {
            return value;
        }

    };

    public static void registerSerializer(IDataSerializer<SpellData> serializer) {
        if (REGISTRY.add(serializer) >= 256) throw new RuntimeException("Vanilla DataSerializer ID limit exceeded");
    }

    static {
        registerSerializer(SPELL_DATA);
    }

    public static <D extends AbstractDanmakuEntity> void shootDanmaku(@NotNull World worldIn, PlayerEntity playerIn,
                                                                      D danmakuEntityType, float velocity, float inaccuracy) {
        Vector3d lookVec = playerIn.getLookVec();

        danmakuEntityType.setNoGravity(true);
        danmakuEntityType.setLocationAndAngles(playerIn.getPosX(), playerIn.getPosY() + playerIn.getEyeHeight(), playerIn.getPosZ(),
                (float) lookVec.y, (float) lookVec.z);
        danmakuEntityType.shoot(lookVec.x, lookVec.y, lookVec.z, velocity, inaccuracy);
        worldIn.addEntity(danmakuEntityType);

    }

    public static <D extends AbstractDanmakuEntity> void shootDanmaku(@NotNull World worldIn, Entity entityIn,
                                                                      D danmakuEntityType, Vector3d shootVec,
                                                                      float velocity, float inaccuracy) {
        Vector3d lookVec = entityIn.getLookVec();

        danmakuEntityType.setNoGravity(true);
        danmakuEntityType.setLocationAndAngles(entityIn.getPosX(), entityIn.getPosY() + entityIn.getEyeHeight(), entityIn.getPosZ(),
                (float) lookVec.y, (float) lookVec.z);
        danmakuEntityType.shoot(shootVec.x, shootVec.y, shootVec.z, velocity, inaccuracy);
        worldIn.addEntity(danmakuEntityType);

    }

    public static <D extends AbstractDanmakuEntity> void initDanmaku(DanmakuData<D> data) {
        data.danmaku.setNoGravity(true);
        data.danmaku.setLocationAndAngles(data.shooter.getPosX(), data.shooter.getPosY() + data.shooter.getEyeHeight(),
                data.shooter.getPosZ(), data.initRotation.x, data.initRotation.y);
    }


    public static void applyOperation(ArrayList<VectorOperations> operations, TransformFunction function, Vector3d prevVec) {
        // operations.forEach(operation -> getTransform(operation, function, prevVec));
    }

    public static Vector3d getTransform(VectorOperations operation, TransformFunction function, Vector3d prevVec) {
        if (function.scaling > 0F) {
            if (operation == VectorOperations.ROTATE_YAW) {
                return prevVec.rotateYaw(function.scaling);
            } else if (operation == VectorOperations.ROTATE_ROLL) {
                return prevVec.rotateRoll(function.scaling);
            } else if (operation == VectorOperations.ROTATE_PITCH) {
                return prevVec.rotatePitch(function.scaling);
            } else if (operation == VectorOperations.VECTOR_SCALE) {
                return prevVec.scale(function.scaling);
            }
        } else if (function.acceleration != null) {
            if (operation == VectorOperations.VECTOR_ADD) {
                return prevVec.add(function.acceleration);
            } else if (operation == VectorOperations.VECTOR_SUBTRACT) {
                return prevVec.subtract(function.acceleration);
            }
        } else if (operation == VectorOperations.ARCHIMEDE_SPIRAL) {
            return getArchimedeSpiral(prevVec, 1.0f, Math.PI);
        }

        return prevVec;
    }

    public static Vector3d getArchimedeSpiral(Vector3d prevVec, double radius, double angle) {
        return new Vector3d(prevVec.x * radius * Math.cos(angle),
                prevVec.y, prevVec.z * radius * Math.signum(angle));
    }

    public static List<Item> getAllDanmakuItem() {
        List<Item> danmakuItems = new ArrayList<>();
        danmakuItems.add(ItemRegistry.LARGE_SHOT_RED.get());
        danmakuItems.add(ItemRegistry.LARGE_SHOT_PURPLE.get());
        danmakuItems.add(ItemRegistry.LARGE_SHOT_BLUE.get());
        danmakuItems.add(ItemRegistry.LARGE_SHOT_GREEN.get());
        danmakuItems.add(ItemRegistry.LARGE_SHOT_YELLOW.get());

        danmakuItems.add(ItemRegistry.SMALL_SHOT_RED.get());
        danmakuItems.add(ItemRegistry.SMALL_SHOT_BLUE.get());
        danmakuItems.add(ItemRegistry.SMALL_SHOT_GREEN.get());
        danmakuItems.add(ItemRegistry.SMALL_SHOT_PURPLE.get());
        danmakuItems.add(ItemRegistry.SMALL_SHOT_YELLOW.get());

        danmakuItems.add(ItemRegistry.HEART_SHOT_RED.get());
        danmakuItems.add(ItemRegistry.HEART_SHOT_PINK.get());
        danmakuItems.add(ItemRegistry.HEART_SHOT_AQUA.get());

        return danmakuItems;
    }

    public static <D extends AbstractDanmakuEntity> void shootSpherical(DanmakuData<D> data, double radius,
                                                                        int latitudeCount, int longitudeCount,
                                                                        int i, int j) {

        AbstractDanmakuEntity danmaku = data.danmaku;

        Vector3d vector3d = new Vector3d(Vector3f.ZP).scale(radius);
        vector3d = vector3d.rotatePitch((float) Math.PI / latitudeCount * i);
        vector3d = vector3d.rotateYaw((float) Math.PI / longitudeCount * j);

        initDanmaku(data);
        danmaku.shoot(vector3d.getX(), vector3d.getY(), vector3d.getZ(), data.speed, 0f);
        data.world.addEntity(danmaku);

    }

    public static void shootSpiral() {

    }

    public static void shootRadial() {

    }

    public static <D extends AbstractDanmakuEntity> void shootAimingAt(DanmakuData<D> data, LivingEntity target) {

    }

    /**
     * 按照玫瑰线来初始化弹幕的位置和旋转
     * @param radius 玫瑰线的半径
     * @param count 玫瑰线花瓣/叶片的数量
     * @param density 玫瑰线上的弹幕密度
     */
    public static void getRoseLinePos(double radius, double count, int density) {

    }

    public static List<Vector2f> getHeartLinePos(float radius) {
        double t = 0;
        double vt = 0.01;
        double maxT = 2 * Math.PI;
        List<Vector2f> positions = new ArrayList<>();

        for (int i = 0; i < Math.ceil(maxT / vt); i++) {
            float x = (float) (16 * GSKOMathUtil.pow3(Math.sin(t)));
            float y = (float) (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t));
            t += vt;
            positions.add(new Vector2f(x * radius, y * radius));
        }
        Vector3d v = new Vector3d(Vector3f.ZP);
        return positions;
    }

}