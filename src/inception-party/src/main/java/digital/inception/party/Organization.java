/*
 * Copyright 2021 Marcus Portmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package digital.inception.party;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import digital.inception.party.constraints.ValidOrganization;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.springframework.util.StringUtils;

/**
 * The <code>Organization</code> class holds the information for an organization, which is an
 * organised group of people with a particular purpose, such as a business or government department.
 *
 * @author Marcus Portmann
 */
@Schema(
    description =
        "An organised group of people with a particular purpose, such as a business or government department")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"type"})
@JsonPropertyOrder({
  "id",
  "tenantId",
  "name",
  "contactMechanisms",
  "physicalAddresses",
  "countriesOfTaxResidence",
  "taxNumbers"
})
@XmlRootElement(name = "Organization", namespace = "http://party.inception.digital")
@XmlType(
    name = "Organization",
    namespace = "http://party.inception.digital",
    propOrder = {"contactMechanisms", "physicalAddresses", "countriesOfTaxResidence", "taxNumbers"})
@XmlAccessorType(XmlAccessType.FIELD)
@ValidOrganization
@Entity
@Table(schema = "party", name = "organizations")
public class Organization extends Party implements Serializable {

  private static final long serialVersionUID = 1000000;

  /** The contact mechanisms for the organization. */
  @Schema(description = "The contact mechanisms for the organization")
  @JsonProperty
  @JsonManagedReference("contactMechanismReference")
  @XmlElementWrapper(name = "ContactMechanisms")
  @XmlElement(name = "ContactMechanism")
  @Valid
  @OneToMany(
      mappedBy = "party",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      orphanRemoval = true)
  private final Set<ContactMechanism> contactMechanisms = new HashSet<>();

  /** The physical addresses for the organization. */
  @Schema(description = "The physical addresses for the organization")
  @JsonProperty
  @JsonManagedReference("physicalAddressReference")
  @XmlElementWrapper(name = "PhysicalAddresses")
  @XmlElement(name = "PhysicalAddress")
  @Valid
  @OneToMany(
      mappedBy = "party",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      orphanRemoval = true)
  private final Set<PhysicalAddress> physicalAddresses = new HashSet<>();

  /** The tax numbers for the organization. */
  @Schema(description = "The tax numbers for the organization")
  @JsonProperty
  @JsonManagedReference("taxNumberReference")
  @XmlElementWrapper(name = "TaxNumbers")
  @XmlElement(name = "TaxNumber")
  @Valid
  @OneToMany(
      mappedBy = "party",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      orphanRemoval = true)
  private final Set<TaxNumber> taxNumbers = new HashSet<>();

  /** The optional comma-delimited codes for the countries of tax residence for the organization. */
  @Size(min = 1, max = 100)
  @Column(table = "organizations", name = "countries_of_tax_residence", length = 100)
  private String countriesOfTaxResidence;

  /** Constructs a new <code>Organization</code>. */
  public Organization() {
    super(PartyType.ORGANIZATION);
  }

  /**
   * Add the contact mechanism for the organization.
   *
   * @param contactMechanism the contact mechanism
   */
  public void addContactMechanism(ContactMechanism contactMechanism) {
    contactMechanisms.removeIf(
        existingContactMechanism ->
            Objects.equals(existingContactMechanism.getType(), contactMechanism.getType())
                && Objects.equals(
                    existingContactMechanism.getPurpose(), contactMechanism.getPurpose()));

    contactMechanism.setParty(this);

    contactMechanisms.add(contactMechanism);
  }

  /**
   * Add the physical address for the organization.
   *
   * @param physicalAddress the physical address
   */
  public void addPhysicalAddress(PhysicalAddress physicalAddress) {
    physicalAddresses.removeIf(
        existingPhysicalAddress ->
            Objects.equals(existingPhysicalAddress.getType(), physicalAddress.getType())
                && Objects.equals(
                    existingPhysicalAddress.getPurpose(), physicalAddress.getPurpose()));

    physicalAddress.setParty(this);

    physicalAddresses.add(physicalAddress);
  }

  /**
   * Add the tax number for the organization.
   *
   * @param taxNumber the tax number
   */
  public void addTaxNumber(TaxNumber taxNumber) {
    taxNumbers.removeIf(
        existingTaxNumber -> Objects.equals(existingTaxNumber.getType(), taxNumber.getType()));

    taxNumber.setParty(this);

    taxNumbers.add(taxNumber);
  }

  /**
   * Retrieve the contact mechanism with the specified type and purpose for the organization.
   *
   * @param type the contact mechanism type
   * @param purpose the contact mechanism purpose
   * @return the contact mechanism with the specified type and purpose for the organization or
   *     <code>null
   *     </code> if the contact mechanism could not be found
   */
  public ContactMechanism getContactMechanism(
      ContactMechanismType type, ContactMechanismPurpose purpose) {
    return contactMechanisms.stream()
        .filter(
            contactMechanism ->
                Objects.equals(contactMechanism.getType(), type)
                    && Objects.equals(contactMechanism.getPurpose(), purpose))
        .findFirst()
        .get();
  }

  /**
   * Returns the contact mechanisms for the organization.
   *
   * @return the contact mechanisms for the organization
   */
  public Set<ContactMechanism> getContactMechanisms() {
    return contactMechanisms;
  }

  /**
   * Returns the optional codes for the countries of tax residence for the organization.
   *
   * @return the optional codes for the countries of tax residence for the organization
   */
  @Schema(
      description = "The optional codes for the countries of tax residence for the organization")
  @JsonProperty(required = true)
  @XmlElement(name = "CountriesOfTaxResidence")
  public String[] getCountriesOfTaxResidence() {
    return StringUtils.commaDelimitedListToStringArray(countriesOfTaxResidence);
  }

  /**
   * Returns the Universally Unique Identifier (UUID) for the organization.
   *
   * @return the Universally Unique Identifier (UUID) for the organization
   */
  @Schema(description = "The Universally Unique Identifier (UUID) for the organization")
  @Override
  public UUID getId() {
    return super.getId();
  }

  /**
   * Returns the name of the organization.
   *
   * @return the name of the organization
   */
  @Schema(description = "The name of the organization")
  @Override
  public String getName() {
    return super.getName();
  }

  /**
   * Retrieve the physical address with the specified type and purpose for the organization.
   *
   * @param type the physical address type
   * @param purpose the physical address purpose
   * @return the physical address with the specified type and purpose for the organization or <code>
   *     null</code> if the physical address could not be found
   */
  public PhysicalAddress getPhysicalAddress(
      PhysicalAddressType type, PhysicalAddressPurpose purpose) {
    return physicalAddresses.stream()
        .filter(
            physicalAddress ->
                Objects.equals(physicalAddress.getType(), type)
                    && Objects.equals(physicalAddress.getPurpose(), purpose))
        .findFirst()
        .get();
  }

  /**
   * Returns the physical addresses for the organization.
   *
   * @return the physical addresses for the organization
   */
  public Set<PhysicalAddress> getPhysicalAddresses() {
    return physicalAddresses;
  }

  /**
   * Retrieve the tax number with the specified type for the organization.
   *
   * @param type the tax number type
   * @return the tax number with the specified type for the organization or <code>null</code> if the
   *     tax number could not be found
   */
  public TaxNumber getTaxNumber(String type) {
    return taxNumbers.stream()
        .filter(taxNumber -> Objects.equals(taxNumber.getType(), type))
        .findFirst()
        .get();
  }

  /**
   * Returns the tax numbers for the organization.
   *
   * @return the tax numbers for the organization
   */
  public Set<TaxNumber> getTaxNumbers() {
    return taxNumbers;
  }

  /**
   * Remove the contact mechanism with the specified type and purpose for the organization.
   *
   * @param type the contact mechanism type
   * @param purpose the contact mechanism purpose
   */
  public void removeContactMechanism(ContactMechanismType type, ContactMechanismPurpose purpose) {
    contactMechanisms.removeIf(
        existingContactMechanism ->
            Objects.equals(existingContactMechanism.getType(), type)
                && Objects.equals(existingContactMechanism.getPurpose(), purpose));
  }

  /**
   * Remove the physical address with the specified type and purpose for the organization.
   *
   * @param type the physical address type
   * @param purpose the physical address purpose
   */
  public void removePhysicalAddress(PhysicalAddressType type, PhysicalAddressPurpose purpose) {
    physicalAddresses.removeIf(
        existingPhysicalAddress ->
            Objects.equals(existingPhysicalAddress.getType(), type)
                && Objects.equals(existingPhysicalAddress.getPurpose(), purpose));
  }

  /**
   * Remove the tax number with the specified type for the organization.
   *
   * @param type the tax number type
   */
  public void removeTaxNumber(String type) {
    taxNumbers.removeIf(existingTaxNumber -> Objects.equals(existingTaxNumber.getType(), type));
  }

  /**
   * Set the contact mechanisms for the organization.
   *
   * @param contactMechanisms the contact mechanisms for the organization
   */
  public void setContactMechanisms(Set<ContactMechanism> contactMechanisms) {
    this.contactMechanisms.clear();
    this.contactMechanisms.addAll(contactMechanisms);
  }

  /**
   * Set the optional codes for the countries of tax residence for the organization.
   *
   * @param countriesOfTaxResidence the optional codes for the countries of tax residence for the
   *     organization
   */
  public void setCountriesOfTaxResidence(String[] countriesOfTaxResidence) {
    this.countriesOfTaxResidence = StringUtils.arrayToDelimitedString(countriesOfTaxResidence, ",");
  }

  /**
   * Set the optional code for the single country of tax residence for the organization.
   *
   * @param countryOfTaxResidence the optional code for the single country of tax residence for the
   *     organization
   */
  public void setCountryOfTaxResidence(String countryOfTaxResidence) {
    this.countriesOfTaxResidence = countryOfTaxResidence;
  }

  /**
   * Set the Universally Unique Identifier (UUID) for the organization.
   *
   * @param id the Universally Unique Identifier (UUID) for the organization
   */
  @Override
  public void setId(UUID id) {
    super.setId(id);
  }

  /**
   * Set the name of the organization.
   *
   * @param name the name of the organization
   */
  @Override
  public void setName(String name) {
    super.setName(name);
  }

  /**
   * Set the physical addresses for the organization.
   *
   * @param physicalAddresses the physical addresses for the organization
   */
  public void setPhysicalAddresses(Set<PhysicalAddress> physicalAddresses) {
    this.physicalAddresses.clear();
    this.physicalAddresses.addAll(physicalAddresses);
  }

  /**
   * Set the tax numbers for the organization.
   *
   * @param taxNumbers the tax numbers for the organization
   */
  public void setTaxNumbers(Set<TaxNumber> taxNumbers) {
    this.taxNumbers.clear();
    this.taxNumbers.addAll(taxNumbers);
  }
}
