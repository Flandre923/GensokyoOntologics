package github.thelawf.gensokyoontology.common.entity;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.api.dialog.DialogTreeNode;
import github.thelawf.gensokyoontology.api.dialog.IEntityDialog;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public abstract class ConversationalEntity extends TameableEntity implements IAngerable, IEntityDialog {

    private DialogTreeNode dialog;
    public static final DataParameter<String> DATA_DIALOG_KEY = EntityDataManager.createKey(
            ConversationalEntity.class, DataSerializers.STRING);

    protected ConversationalEntity(EntityType<? extends TameableEntity> type, World worldIn) {
        super(type, worldIn);
        this.dialog = new DialogTreeNode("dialog." + GensokyoOntology.MODID + ".hello");
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_DIALOG_KEY, this.dialog.getName());
    }

    @Override
    public void readAdditional(@NotNull CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("dialog_key")) {
            this.dialog = new DialogTreeNode(compound.getString("dialog_key"));
        }
    }

    @Override
    public void writeAdditional(@NotNull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putString("dialog_key", this.dialog.getName());
    }

    @Override
    public DialogTreeNode getDialog() {
        return this.dialog;
    }

    @Override
    public void setDialog(DialogTreeNode dialog) {
        this.dialog = dialog;
    }
}