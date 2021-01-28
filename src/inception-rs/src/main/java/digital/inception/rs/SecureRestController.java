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

package digital.inception.rs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

/**
 * The <b>SecureRestController</b> class provides the base class from which all secure RESTful
 * controllers should be derived.
 *
 * @author Marcus Portmann
 */
@SuppressWarnings("unused")
public abstract class SecureRestController {

  /**
   * Returns the <b>Long</b> value portion of the authorities with the specified prefix.
   *
   * @param authentication the authenticated principal associated with the authenticated request
   * @param prefix the authority prefix
   * @return the <b>Long</b> value portion of the authorities with the specified prefix
   */
  protected List<Long> getLongValuesForAuthoritiesWithPrefix(
      Authentication authentication, String prefix) {
    var values = new ArrayList<Long>();

    for (GrantedAuthority authority : authentication.getAuthorities()) {
      if (authority.getAuthority().startsWith(prefix)) {
        try {
          values.add(Long.parseLong(authority.getAuthority().substring(prefix.length())));
        } catch (Throwable ignored) {
        }
      }
    }

    return values;
  }

  /**
   * Returns the <b>UUID</b> value portion of the authorities with the specified prefix.
   *
   * @param authentication the authenticated principal associated with the authenticated request
   * @param prefix the authority prefix
   * @return the <b>UUID</b> value portion of the authorities with the specified prefix
   */
  protected List<UUID> getUUIDValuesForAuthoritiesWithPrefix(
      Authentication authentication, String prefix) {
    var values = new ArrayList<UUID>();

    for (GrantedAuthority authority : authentication.getAuthorities()) {
      if (authority.getAuthority().startsWith(prefix)) {
        try {
          values.add(UUID.fromString(authority.getAuthority().substring(prefix.length())));
        } catch (Throwable ignored) {
        }
      }
    }

    return values;
  }

  /**
   * Returns the value portion of the authority with the specified prefix.
   *
   * @param authentication the authenticated principal associated with the authenticated request
   * @param prefix the authority prefix
   * @return the value portion of the authority with the specified prefix or <b>null</b> if
   *     the authority with the specified prefix could not be found
   */
  protected String getValueForAuthorityWithPrefix(Authentication authentication, String prefix) {
    for (GrantedAuthority authority : authentication.getAuthorities()) {
      if (authority.getAuthority().startsWith(prefix)) {
        return authority.getAuthority().substring(prefix.length());
      }
    }

    return null;
  }

  /**
   * Returns the value portion of the authorities with the specified prefix.
   *
   * @param authentication the authenticated principal associated with the authenticated request
   * @param prefix the authority prefix
   * @return the value portion of the authorities with the specified prefix
   */
  protected List<String> getValuesForAuthoritiesWithPrefix(
      Authentication authentication, String prefix) {
    var values = new ArrayList<String>();

    for (GrantedAuthority authority : authentication.getAuthorities()) {
      if (authority.getAuthority().startsWith(prefix)) {
        values.add(authority.getAuthority().substring(prefix.length()));
      }
    }

    return values;
  }

  /**
   * Confirm that the user associated with the authenticated request has access to the specified
   * function.
   *
   * @param functionCode the code for the function
   * @return <b>true</b> if the user associated with the authenticated request has access to
   *     the function identified by the specified function code or <b>false</b> otherwise
   */
  protected boolean hasAccessToFunction(String functionCode) {
    return hasAuthority("FUNCTION_" + functionCode);
  }

  /**
   * Confirm that the user associated with the authenticated request has the specified authority.
   *
   * @param authority the authority
   * @return <b>true</b> if the user associated with the authenticated request has the
   *     specified authority or <b>false</b> otherwise
   */
  protected boolean hasAuthority(String authority) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // Could not retrieve the currently authenticated principal
    if (authentication == null) {
      return false;
    }

    if (!StringUtils.hasText(authority)) {
      return false;
    }

    if (!authentication.isAuthenticated()) {
      return false;
    }

    for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
      if (grantedAuthority.getAuthority().equalsIgnoreCase(authority)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Confirm that the user associated with the authenticated request has the specified role.
   *
   * @param roleName the name of the role
   * @return <b>true</b> if the user associated with the authenticated request has the
   *     specified role or <b>false</b> otherwise
   */
  protected boolean hasRole(String roleName) {
    return hasAuthority("ROLE_" + roleName);
  }
}
