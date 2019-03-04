package vip.wulang.spring.core;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author CoolerWu on 2019/3/2.
 * @version 1.0
 */
public class ServiceProperty {
    private Object owner;
    private Class ownerClass;
    private Class[] interfaces;
    private Class superClass;
    private String ownerName;

    ServiceProperty(Object owner, Class ownerClass, Class[] interfaces,
                           Class superClass, String ownerName) {
        this.owner = owner;
        this.ownerClass = ownerClass;
        this.interfaces = interfaces;
        this.superClass = superClass;
        this.ownerName = ownerName;
    }

    Object getOwner() {
        return owner;
    }

    Class getOwnerClass() {
        return ownerClass;
    }

    Class[] getInterfaces() {
        return interfaces;
    }

    Class getSuperClass() {
        return superClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceProperty that = (ServiceProperty) o;
        return Objects.equals(owner, that.owner) &&
                Objects.equals(ownerClass, that.ownerClass) &&
                Arrays.equals(interfaces, that.interfaces) &&
                Objects.equals(superClass, that.superClass) &&
                Objects.equals(ownerName, that.ownerName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(owner, ownerClass, superClass, ownerName);
        result = 31 * result + Arrays.hashCode(interfaces);
        return result;
    }

    @Override
    public String toString() {
        return "ServiceProperty{" +
                "owner=" + owner +
                ", ownerClass=" + ownerClass +
                ", interfaces=" + Arrays.toString(interfaces) +
                ", superClass=" + superClass +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }
}
