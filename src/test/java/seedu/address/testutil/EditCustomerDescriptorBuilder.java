package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditCustomerDescriptor;
import seedu.address.model.customer.Address;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.DateTime;
import seedu.address.model.customer.Diners;
import seedu.address.model.customer.Email;
import seedu.address.model.customer.Name;
import seedu.address.model.customer.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditCustomerDescriptor objects.
 */
public class EditCustomerDescriptorBuilder {

    private EditCustomerDescriptor descriptor;

    public EditCustomerDescriptorBuilder() {
        descriptor = new EditCommand.EditCustomerDescriptor();
    }

    public EditCustomerDescriptorBuilder(EditCommand.EditCustomerDescriptor descriptor) {
        this.descriptor = new EditCommand.EditCustomerDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCustomerDescriptor} with fields containing {@code customer}'s details
     */
    public EditCustomerDescriptorBuilder(Customer customer) {
        descriptor = new EditCustomerDescriptor();
        descriptor.setName(customer.getName());
        descriptor.setPhone(customer.getPhone());
        descriptor.setEmail(customer.getEmail());
        descriptor.setAddress(customer.getAddress());
        descriptor.setDiners(customer.getDiners());
        descriptor.setDateTime(customer.getDateTime());
        descriptor.setTags(customer.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Diners} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withDiners(String diners) {
        descriptor.setDiners(new Diners(diners));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withDateTime(String dateTime) {
        descriptor.setDateTime(new DateTime(dateTime));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditCustomerDescriptor}
     * that we are building.
     */
    public EditCustomerDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCustomerDescriptor build() {
        return descriptor;
    }
}
