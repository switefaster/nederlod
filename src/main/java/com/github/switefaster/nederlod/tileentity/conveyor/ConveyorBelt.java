package com.github.switefaster.nederlod.tileentity.conveyor;

import com.github.switefaster.nederlod.tileentity.ItemConveyorTileEntity;
import com.github.switefaster.nederlod.util.math.vector.Vec3f;
import com.github.switefaster.nederlod.util.physics.Constants;
import javafx.util.Pair;
import net.minecraft.util.Direction;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ConveyorBelt {
    //fractional coefficient
    public static final float DEFAULT_MU = 0.5f;

    private final LinkedList<ConveyedItem> items = new LinkedList<>();
    private int minEndPoint;
    private int maxEndPoint;
    private Vec3f minEndPos;
    private Direction.Axis direction;
    private float speed;
    private float power;
    private ItemConveyorTileEntity commander;

    public static float getCorrespondingComponentForAxis(Direction.Axis axis, Vec3f vector) {
        return axis == Direction.Axis.X ? vector.getX() : vector.getZ();
    }

    public Direction.Axis getDirection() {
        return direction;
    }

    public int getMinEndPoint() {
        return minEndPoint;
    }

    public int getMaxEndPoint() {
        return maxEndPoint;
    }

    public List<ConveyedItem> getItemsOnBelt(int ordinal) {
        if (ordinal > maxEndPoint - minEndPoint) {
            throw new ArrayIndexOutOfBoundsException("Invalid ordinal for belt: " + ordinal);
        }
        return items.stream().filter(item -> (direction == Direction.Axis.X) ?
                (item.getCentroid().getX() >= minEndPoint + ordinal && item.getCentroid().getX() < minEndPoint + ordinal + 1)
                : (item.getCentroid().getZ() >= minEndPoint + ordinal && item.getCentroid().getZ() < minEndPoint + ordinal + 1))
                .collect(Collectors.toList());
    }

    public Pair<Integer, ConveyedItem> getFirstItemOnBelt(int ordinal) {
        if (ordinal > maxEndPoint - minEndPoint) {
            throw new ArrayIndexOutOfBoundsException("Invalid ordinal for belt: " + ordinal);
        }
        int index = 0;
        for (ConveyedItem item : items) {
            float pos = getCorrespondingComponentForAxis(direction, item.getCentroid());
            if (pos >= minEndPoint + ordinal && pos < minEndPoint + ordinal + 1) {
                return new Pair<>(index, item);
            }
            index++;
        }
        return new Pair<>(-1, null);
    }

    public int findIndexForPosition(float position) {
        int l = 0, r = items.size() - 1, res = r;
        while (l <= r) {
            int mid = l + (r - l) >> 1;
            ConveyedItem elector = items.get(mid);
            float offset = getCorrespondingComponentForAxis(direction, elector.getCentroid()) - minEndPoint;
            if (offset > position) {
                res = mid;
                r = mid - 1;
            } else {
                l = mid;
            }
        }
        return res;
    }

    public ConveyedItem getItemAtOffset(float offset) {
        int l = 0, r = items.size() - 1;
        while (l < r) {
            int mid = l + (r - l) >> 1;
            ConveyedItem elector = items.get(mid);
            float position = getCorrespondingComponentForAxis(direction, elector.getCentroid()) - minEndPoint;
            if (position > offset) {
                r = mid - 1;
            } else if (position < offset) {
                l = mid + 1;
            } else {
                return elector;
            }
        }
        return null;
    }

    public void insertItemAt(ConveyedItem item, int index) {
        items.add(index, item);
    }

    public void removeItem(ConveyedItem item) {
        items.remove(item);
    }

    public void removeAtIndex(int index) {
        items.remove(index);
    }

    public ConveyedItem getItemAtIndex(int index) {
        return items.get(index);
    }

    public void tick() {
        for (ConveyedItem item : items) {
            item.force(Constants.GRAVITY.copy().mul(item.getMass()));
        }
    }

    public Vec3f getActualPosition(float offset) {
        if (direction == Direction.Axis.X) {
            return minEndPos.copy().add(new Vec3f(offset, 0.0f, 0.0f));
        }
        return minEndPos.copy().add(new Vec3f(0.0f, 0.0f, offset));
    }

    public void markAndNotify() {
        this.commander.needUpdate();
    }
}
