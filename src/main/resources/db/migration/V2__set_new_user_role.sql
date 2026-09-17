INSERT INTO roles (name)
VALUES ('USER')
    ON CONFLICT (name) DO NOTHING;


CREATE OR REPLACE FUNCTION assign_default_user_role()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO user_roles (user_id, role_id)
    SELECT NEW.id, id
    FROM roles
    WHERE name = 'USER';
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_assign_default_user_role
    AFTER INSERT ON users
    FOR EACH ROW
    EXECUTE FUNCTION assign_default_user_role();